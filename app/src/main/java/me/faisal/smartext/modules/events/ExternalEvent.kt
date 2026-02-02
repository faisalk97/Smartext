package me.faisal.smartext.modules.events

data class ExternalEvent(
    val type: ExternalEventType,
    val data: String?,
)
