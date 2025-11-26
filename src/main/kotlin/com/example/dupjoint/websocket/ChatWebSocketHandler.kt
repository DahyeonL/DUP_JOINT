package com.example.dupjoint.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

@Component
class ChatWebSocketHandler(
    private val objectMapper: ObjectMapper,
) : TextWebSocketHandler() {
    private val sessions: MutableMap<String, WebSocketSession> = ConcurrentHashMap()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions[session.id] = session
        val joinMessage = ChatMessage(
            sender = "System",
            message = "${session.id.take(6)} joined the room",
            timestamp = Instant.now(),
        )
        broadcast(joinMessage)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = parsePayload(message.payload)
        val chatMessage = ChatMessage(
            sender = payload.sender.ifBlank { "Guest" },
            message = payload.message.trim(),
            timestamp = Instant.now(),
        )
        broadcast(chatMessage)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session.id)
        val leaveMessage = ChatMessage(
            sender = "System",
            message = "${session.id.take(6)} left the room",
            timestamp = Instant.now(),
        )
        broadcast(leaveMessage)
    }

    private fun parsePayload(raw: String): IncomingMessage =
        runCatching { objectMapper.readValue<IncomingMessage>(raw) }
            .getOrElse { IncomingMessage(sender = "Guest", message = raw) }

    private fun broadcast(message: ChatMessage) {
        val serialized = objectMapper.writeValueAsString(message)
        sessions.values.forEach { session ->
            if (session.isOpen) {
                session.sendMessage(TextMessage(serialized))
            }
        }
    }
}

data class IncomingMessage(
    val sender: String = "Guest",
    val message: String,
)

data class ChatMessage(
    val sender: String,
    val message: String,
    val timestamp: Instant,
)
