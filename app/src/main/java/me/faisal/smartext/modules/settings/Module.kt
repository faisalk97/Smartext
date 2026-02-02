package me.faisal.smartext.modules.settings

import androidx.preference.PreferenceManager
import me.faisal.smartext.helpers.SettingsHelper
import me.faisal.smartext.modules.encryption.EncryptionSettings
import me.faisal.smartext.modules.gateway.GatewaySettings
import me.faisal.smartext.modules.localserver.LocalServerSettings
import me.faisal.smartext.modules.logs.LogsSettings
import me.faisal.smartext.modules.messages.MessagesSettings
import me.faisal.smartext.modules.ping.PingSettings
import me.faisal.smartext.modules.webhooks.TemporaryStorage
import me.faisal.smartext.modules.webhooks.WebhooksSettings
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val settingsModule = module {
    singleOf(::SettingsService)
    factory { PreferenceManager.getDefaultSharedPreferences(get()) }
    factory { SettingsHelper(get()) }

    factory {
        EncryptionSettings(
            PreferencesStorage(get(), "encryption")
        )
    }
    factory {
        GatewaySettings(
            PreferencesStorage(get(), "gateway")
        )
    }
    factory {
        MessagesSettings(
            PreferencesStorage(get(), "messages")
        )
    }
    factory {
        LocalServerSettings(
            PreferencesStorage(get(), "localserver")
        )
    }
    factory {
        PingSettings(
            PreferencesStorage(get(), "ping")
        )
    }
    factory {
        LogsSettings(
            PreferencesStorage(get(), "logs")
        )
    }
    factory {
        WebhooksSettings(
            PreferencesStorage(get(), "webhooks")
        )
    }
    factory {
        TemporaryStorage(
            PreferencesStorage(get(), "webhooks")
        )
    }
}