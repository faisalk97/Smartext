import me.faisal.smartext.modules.health.HealthService
import me.faisal.smartext.modules.health.monitors.BatteryMonitor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val healthModule = module {
    singleOf(::BatteryMonitor)
    singleOf(::HealthService)
}