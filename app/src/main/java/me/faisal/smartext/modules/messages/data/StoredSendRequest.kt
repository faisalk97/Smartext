package me.faisal.smartext.modules.messages.data

import me.faisal.smartext.data.entities.MessageRecipient
import me.faisal.smartext.domain.EntitySource
import me.faisal.smartext.domain.ProcessingState

class StoredSendRequest(
    val id: Long,
    val state: ProcessingState,
    val recipients: List<MessageRecipient>,
    source: EntitySource,
    message: Message,
    params: SendParams
) :
    SendRequest(source, message, params)