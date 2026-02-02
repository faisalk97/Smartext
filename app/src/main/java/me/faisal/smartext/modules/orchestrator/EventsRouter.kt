package me.faisal.smartext.modules.orchestrator

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import me.faisal.smartext.modules.events.EventBus
import me.faisal.smartext.modules.events.ExternalEvent
import me.faisal.smartext.modules.events.ExternalEventType
import me.faisal.smartext.modules.gateway.events.MessageEnqueuedEvent
import me.faisal.smartext.modules.gateway.events.SettingsUpdatedEvent
import me.faisal.smartext.modules.gateway.events.WebhooksUpdatedEvent
import me.faisal.smartext.modules.receiver.events.MessagesExportRequestedEvent

class EventsRouter(
    private val eventBus: EventBus
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun route(event: ExternalEvent) {
        scope.launch {
            when (event.type) {
                ExternalEventType.MessageEnqueued ->
                    eventBus.emit(
                        MessageEnqueuedEvent()
                    )

                ExternalEventType.WebhooksUpdated ->
                    eventBus.emit(
                        WebhooksUpdatedEvent()
                    )

                ExternalEventType.MessagesExportRequested ->
                    eventBus.emit(
                        MessagesExportRequestedEvent.withPayload(
                            requireNotNull(event.data)
                        )
                    )

                ExternalEventType.SettingsUpdated ->
                    eventBus.emit(
                        SettingsUpdatedEvent()
                    )
            }
        }
    }
}