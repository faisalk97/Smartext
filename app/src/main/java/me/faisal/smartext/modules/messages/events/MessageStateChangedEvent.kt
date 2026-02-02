package me.faisal.smartext.modules.messages.events

import me.faisal.smartext.domain.EntitySource
import me.faisal.smartext.domain.ProcessingState
import me.faisal.smartext.modules.events.AppEvent

class MessageStateChangedEvent(
    val id: String,
    val source: EntitySource,
    val phoneNumbers: Set<String>,
    val state: ProcessingState,
    val simNumber: Int?,
    val partsCount: Int?,
    val error: String?
): AppEvent(NAME) {

    companion object {
        const val NAME = "MessageStateChangedEvent"
    }
}