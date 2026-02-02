package me.faisal.smartext.modules.settings

import android.content.Context
import me.faisal.smartext.R
import me.faisal.smartext.modules.encryption.EncryptionSettings
import me.faisal.smartext.modules.gateway.GatewaySettings
import me.faisal.smartext.modules.logs.LogsSettings
import me.faisal.smartext.modules.messages.MessagesSettings
import me.faisal.smartext.modules.notifications.NotificationsService
import me.faisal.smartext.modules.ping.PingSettings
import me.faisal.smartext.modules.webhooks.WebhooksSettings

class SettingsService(
    private val context: Context,
    private val notificationsService: NotificationsService,
    encryptionSettings: EncryptionSettings,
    gatewaySettings: GatewaySettings,
    messagesSettings: MessagesSettings,
    pingSettings: PingSettings,
    logsSettings: LogsSettings,
    webhooksSettings: WebhooksSettings
) {
    private val settings = mapOf(
        "encryption" to encryptionSettings,
        "gateway" to gatewaySettings,
        "messages" to messagesSettings,
        "ping" to pingSettings,
        "logs" to logsSettings,
        "webhooks" to webhooksSettings
    )

    fun getAll(): Map<String, *> {
        return settings.mapValues { (it.value as? Exporter)?.export() }
    }

    fun update(data: Map<String, *>) {
        if (data.isEmpty()) {
            return
        }

        val changed = data.map { (key, value) ->
            try {
                settings[key]?.let {
                    (it as? Importer)?.import(value as Map<String, *>)
                }
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Failed to import $key: ${e.message}", e)
            }
        }

        if (changed.none { it == true }) {
            return
        }

        notificationsService.notify(
            context,
            NotificationsService.NOTIFICATION_ID_SETTINGS_CHANGED,
            context.getString(R.string.settings_changed_via_api_restart_the_app_to_apply_changes)
        )
    }
}