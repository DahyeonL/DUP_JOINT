package com.joint.api

import org.springframework.stereotype.Component
import java.time.Instant

@Component
class InMemoryStore {
    val users = listOf(
        User(id = "u-1", name = "이은지", email = "owner@joint.io", organization = "JOINT", role = Role.OWNER),
        User(id = "u-2", name = "김민수", email = "member@joint.io", organization = "JOINT", role = Role.MEMBER),
        User(id = "u-3", name = "박하늘", email = "partner@acme.com", organization = "ACME", role = Role.MEMBER)
    )

    val partners = listOf(
        Partner(id = "p-1", name = "ACME", memo = "보안 솔루션 공급사", primaryContact = Contact("박하늘", "partner@acme.com")),
        Partner(id = "p-2", name = "STUDIO SPRING", memo = "브랜딩 협업", primaryContact = null)
    )

    val conversations = listOf(
        Conversation(
            id = "c-1",
            name = "ACME 온보딩",
            partnerId = "p-1",
            isPrivate = false,
            participants = users,
            lastMessage = null
        ),
        Conversation(
            id = "c-2",
            name = "STUDIO SPRING 디자인 체크인",
            partnerId = "p-2",
            isPrivate = true,
            participants = users.take(2),
            lastMessage = null
        )
    )

    val messages = listOf(
        Message(
            id = "m-1",
            conversationId = "c-1",
            sender = users.first(),
            content = "환영합니다! 프로젝트 킥오프를 위해 자료를 공유드려요.",
            createdAt = Instant.now().minusSeconds(3600),
            reactions = listOf(Reaction("👍", 2, reactedByMe = true)),
            important = true
        ),
        Message(
            id = "m-2",
            conversationId = "c-1",
            sender = users[2],
            content = "자료 확인했습니다. 일정 공유 부탁드립니다.",
            createdAt = Instant.now().minusSeconds(1200)
        )
    )
}
