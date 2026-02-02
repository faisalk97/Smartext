package me.faisal.smartext.modules.gateway.events

import me.faisal.smartext.modules.events.AppEvent

class WebhooksUpdatedEvent : AppEvent(NAME) {
    companion object {
        const val NAME = "WebhooksUpdatedEvent"
    }
}