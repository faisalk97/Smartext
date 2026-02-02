package me.faisal.smartext.modules.health.domain

data class HealthResult(
    val status: Status,
    val checks: Map<String, CheckResult>
)
