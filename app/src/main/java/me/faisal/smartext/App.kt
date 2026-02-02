package me.faisal.smartext

import android.app.Application
import healthModule
import me.faisal.smartext.data.dbModule
import me.faisal.smartext.modules.connection.connectionModule
import me.faisal.smartext.modules.encryption.encryptionModule
import me.faisal.smartext.modules.events.eventBusModule
import me.faisal.smartext.modules.gateway.GatewayService
import me.faisal.smartext.modules.localserver.localserverModule
import me.faisal.smartext.modules.logs.logsModule
import me.faisal.smartext.modules.messages.messagesModule
import me.faisal.smartext.modules.notifications.notificationsModule
import me.faisal.smartext.modules.orchestrator.OrchestratorService
import me.faisal.smartext.modules.orchestrator.orchestratorModule
import me.faisal.smartext.modules.ping.pingModule
import me.faisal.smartext.modules.receiver.receiverModule
import me.faisal.smartext.modules.settings.settingsModule
import me.faisal.smartext.modules.webhooks.webhooksModule
import me.faisal.smartext.receivers.EventsReceiver
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                eventBusModule,
                settingsModule,
                dbModule,
                logsModule,
                notificationsModule,
                messagesModule,
                receiverModule,
                encryptionModule,
                me.faisal.smartext.modules.gateway.gatewayModule,
                healthModule,
                webhooksModule,
                localserverModule,
                pingModule,
                connectionModule,
                orchestratorModule,
            )
        }

        Thread.setDefaultUncaughtExceptionHandler(
            GlobalExceptionHandler(
                Thread.getDefaultUncaughtExceptionHandler()!!,
                get()
            )
        )

        instance = this

        EventsReceiver.register(this)

        get<OrchestratorService>().start(this, true)
    }

    val gatewayService: GatewayService by inject()

    companion object {
        lateinit var instance: App
            private set
    }
}
