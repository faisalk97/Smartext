package me.faisal.smartext.modules.gateway.events

import me.faisal.smartext.modules.events.AppEvent

class MessageEnqueuedEvent : AppEvent(NAME) {
    companion object {
        const val NAME = "MessageEnqueuedEvent"
    }
}