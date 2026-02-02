package me.faisal.smartext.modules.gateway.events

import me.faisal.smartext.modules.events.AppEvent

sealed class DeviceRegisteredEvent(
    val server: String,
) : AppEvent(NAME) {
    class Success(
        server: String,
        val login: String,
        val password: String?,
    ) : DeviceRegisteredEvent(server)

    class Failure(
        server: String,
        val reason: String,
    ) : DeviceRegisteredEvent(server)

    companion object {
        const val NAME = "DeviceRegisteredEvent"
    }
}