package me.faisal.smartext.modules.webhooks

import android.util.Log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.faisal.smartext.domain.ProcessingState
import me.faisal.smartext.modules.events.EventBus
import me.faisal.smartext.modules.events.EventsReceiver
import me.faisal.smartext.modules.messages.events.MessageStateChangedEvent
import me.faisal.smartext.modules.ping.events.PingEvent
import me.faisal.smartext.modules.webhooks.domain.WebHookEvent
import me.faisal.smartext.modules.webhooks.payload.SmsEventPayload
import org.koin.core.component.get
import java.util.Date

class EventsReceiver : EventsReceiver() {
    override suspend fun collect(eventBus: EventBus) {
        coroutineScope {
            launch {
                eventBus.collect<PingEvent> {
                    Log.d("EventsReceiver", "Event: $it")

                    get<WebHooksService>().emit(
                        get(),
                        WebHookEvent.SystemPing,
                        mapOf("health" to it.health)
                    )
                }
            }

            launch {
                eventBus.collect<MessageStateChangedEvent> { event ->
                    Log.d("EventsReceiver", "Event: $event")

                    val webhookEventType = when (event.state) {
                        ProcessingState.Sent -> WebHookEvent.SmsSent
                        ProcessingState.Delivered -> WebHookEvent.SmsDelivered
                        ProcessingState.Failed -> WebHookEvent.SmsFailed
                        else -> return@collect
                    }

                    event.phoneNumbers.forEach { phoneNumber ->
                        val payload = when (webhookEventType) {
                            WebHookEvent.SmsSent -> SmsEventPayload.SmsSent(
                                messageId = event.id,
                                phoneNumber = phoneNumber,
                                event.simNumber,
                                partsCount = event.partsCount ?: -1,
                                sentAt = Date(),
                            )

                            WebHookEvent.SmsDelivered -> SmsEventPayload.SmsDelivered(
                                messageId = event.id,
                                phoneNumber = phoneNumber,
                                event.simNumber,
                                deliveredAt = Date(),
                            )

                            WebHookEvent.SmsFailed -> SmsEventPayload.SmsFailed(
                                messageId = event.id,
                                phoneNumber = phoneNumber,
                                event.simNumber,
                                failedAt = Date(),
                                reason = event.error ?: "Unknown",
                            )

                            else -> return@forEach
                        }

                        get<WebHooksService>().emit(
                            get(), webhookEventType, payload
                        )
                    }
                }
            }
        }
    }
}