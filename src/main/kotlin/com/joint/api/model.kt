package com.joint.api

import java.time.Instant

enum class Role { OWNER, ADMIN, MEMBER }

data class Team(
    val id: String,
    val name: String,
    val members: List<User>
)

data class Partner(
    val id: String,
    val name: String,
    val memo: String?,
    val primaryContact: Contact?
)

data class Conversation(
    val id: String,
    val name: String,
    val partnerId: String,
    val isPrivate: Boolean,
    val participants: List<User>,
    val lastMessage: Message?
)

data class Message(
    val id: String,
    val conversationId: String,
    val sender: User,
    val content: String,
    val createdAt: Instant,
    val reactions: List<Reaction> = emptyList(),
    val important: Boolean = false
)

data class Reaction(
    val emoji: String,
    val count: Int,
    val reactedByMe: Boolean
)

data class User(
    val id: String,
    val name: String,
    val email: String,
    val organization: String,
    val role: Role = Role.MEMBER
)

data class Contact(
    val name: String,
    val email: String
)

data class DashboardStats(
    val partnerCount: Int,
    val conversationCount: Int,
    val messageCount: Int,
    val internalMessageCount: Int
)
