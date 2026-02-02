package me.faisal.smartext.domain

import me.faisal.smartext.BuildConfig
import me.faisal.smartext.modules.health.domain.CheckResult
import me.faisal.smartext.modules.health.domain.HealthResult
import me.faisal.smartext.modules.health.domain.Status

class HealthResponse(
    healthResult: HealthResult,

    val version: String = BuildConfig.VERSION_NAME,
    val releaseId: Int = BuildConfig.VERSION_CODE,
) {
    val status: Status = healthResult.status
    val checks: Map<String, CheckResult> = healthResult.checks
}