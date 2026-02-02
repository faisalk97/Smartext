package me.faisal.smartext.modules.ping.events

import me.faisal.smartext.domain.HealthResponse
import me.faisal.smartext.modules.events.AppEvent

class PingEvent(
    val health: HealthResponse,
) : AppEvent(TYPE) {
    companion object {
        const val TYPE = "PingEvent"
    }
}