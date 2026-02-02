package me.faisal.smartext.modules.localserver.events

import me.faisal.smartext.modules.events.AppEvent

class IPReceivedEvent(
    val localIP: String?,
    val publicIP: String?,
): AppEvent(NAME) {
    companion object {
        const val NAME = "IPReceivedEvent"
    }
}