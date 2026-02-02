package me.faisal.smartext.modules.gateway

import android.util.Log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.faisal.smartext.domain.EntitySource
import me.faisal.smartext.modules.events.EventBus
import me.faisal.smartext.modules.events.EventsReceiver
import me.faisal.smartext.modules.gateway.events.DeviceRegisteredEvent
import me.faisal.smartext.modules.gateway.events.MessageEnqueuedEvent
import me.faisal.smartext.modules.gateway.events.SettingsUpdatedEvent
import me.faisal.smartext.modules.gateway.events.WebhooksUpdatedEvent
import me.faisal.smartext.modules.gateway.services.SSEForegroundService
import me.faisal.smartext.modules.gateway.workers.PullMessagesWorker
import me.faisal.smartext.modules.gateway.workers.SendStateWorker
import me.faisal.smartext.modules.gateway.workers.SettingsUpdateWorker
import me.faisal.smartext.modules.gateway.workers.WebhooksUpdateWorker
import me.faisal.smartext.modules.messages.events.MessageStateChangedEvent
import me.faisal.smartext.modules.ping.events.PingEvent
import org.koin.core.component.get

class EventsReceiver : EventsReceiver() {

    private val settings = get<GatewaySettings>()

    override suspend fun collect(eventBus: EventBus) {
        coroutineScope {
            launch {
                Log.d("EventsReceiver", "launched MessageEnqueuedEvent")
                eventBus.collect<MessageEnqueuedEvent> { event ->
                    Log.d("EventsReceiver", "Event: $event")

                    if (!settings.enabled) return@collect

                    PullMessagesWorker.start(get())
                }
            }
            launch {
                Log.d("EventsReceiver", "launched MessageStateChangedEvent")
                val allowedSources = setOf(EntitySource.Cloud, EntitySource.Gateway)
                eventBus.collect<MessageStateChangedEvent> { event ->
                    Log.d("EventsReceiver", "Event: $event")

                    if (!settings.enabled) return@collect

                    if (event.source !in allowedSources) return@collect

                    SendStateWorker.start(get(), event.id)
                }
            }

            launch {
                Log.d("EventsReceiver", "launched PingEvent")
                eventBus.collect<PingEvent> {
                    Log.d("EventsReceiver", "Event: $it")

                    if (!settings.enabled) return@collect

                    PullMessagesWorker.start(get())
                }
            }

            launch {
                Log.d("EventsReceiver", "launched WebhooksUpdatedEvent")
                eventBus.collect<WebhooksUpdatedEvent> {
                    Log.d("EventsReceiver", "Event: $it")

                    if (!settings.enabled) return@collect

                    WebhooksUpdateWorker.start(get())
                }
            }

            launch {
                Log.d("EventsReceiver", "launched SettingsUpdatedEvent")
                eventBus.collect<SettingsUpdatedEvent> {
                    Log.d("EventsReceiver", "Event: $it")

                    if (!settings.enabled) return@collect

                    SettingsUpdateWorker.start(get())
                }
            }

            launch {
                Log.d("EventsReceiver", "launched DeviceRegisteredEvent")
                eventBus.collect<DeviceRegisteredEvent> {
                    Log.d("EventsReceiver", "Event: $it")

                    if (!settings.enabled) return@collect
                    if (settings.fcmToken != null) return@collect

                    SSEForegroundService.start(get())
                }
            }
        }

    }
}