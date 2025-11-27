package com.joint.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ApiController(private val store: InMemoryStore) {

    @GetMapping("/teams")
    fun getTeam(): Team =
        Team(id = "t-1", name = "JOINT", members = store.users.filter { it.organization == "JOINT" })

    @GetMapping("/partners")
    fun getPartners(): List<Partner> = store.partners

    @GetMapping("/conversations")
    fun getConversations(): List<Conversation> = store.conversations.map { conversation ->
        val lastMessage = store.messages.filter { it.conversationId == conversation.id }.maxByOrNull { it.createdAt }
        conversation.copy(lastMessage = lastMessage)
    }

    @GetMapping("/conversations/{id}/messages")
    fun getConversationMessages(@PathVariable id: String): List<Message> =
        store.messages.filter { it.conversationId == id }

    @GetMapping("/dashboard")
    fun getDashboard(): DashboardStats {
        val partnerCount = store.partners.size
        val conversationCount = store.conversations.size
        val messageCount = store.messages.size
        val internalMessageCount = store.messages.count { it.sender.organization == "JOINT" }

        return DashboardStats(
            partnerCount = partnerCount,
            conversationCount = conversationCount,
            messageCount = messageCount,
            internalMessageCount = internalMessageCount
        )
    }
}
