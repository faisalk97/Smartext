package me.faisal.smartext.modules.webhooks.domain

import me.faisal.smartext.domain.EntitySource

data class WebHookDTO(
    val id: String?,
    val deviceId: String?,
    val url: String,
    val event: WebHookEvent,
    val source: EntitySource,
)
