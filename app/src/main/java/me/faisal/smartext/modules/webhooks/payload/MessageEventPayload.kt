package me.faisal.smartext.modules.webhooks.payload

abstract class MessageEventPayload(
    val messageId: String,
    val phoneNumber: String,
    val simNumber: Int?,
)