package me.faisal.smartext.modules.messages.data

import me.faisal.smartext.domain.MessageContent
import java.util.Date

data class Message(
    val id: String,
    val content: MessageContent,
    val phoneNumbers: List<String>,

    val isEncrypted: Boolean,

    val createdAt: Date,
)