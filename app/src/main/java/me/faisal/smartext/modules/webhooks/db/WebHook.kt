package me.faisal.smartext.modules.webhooks.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.faisal.smartext.domain.EntitySource
import me.faisal.smartext.modules.webhooks.domain.WebHookEvent

@Entity
data class WebHook(
    @PrimaryKey
    val id: String,
    val url: String,
    val event: WebHookEvent,
    val source: EntitySource,
)