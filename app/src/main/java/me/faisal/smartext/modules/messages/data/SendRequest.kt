package me.faisal.smartext.modules.messages.data

import me.faisal.smartext.domain.EntitySource

open class SendRequest(
    val source: EntitySource,
    val message: Message,
    val params: SendParams,
)