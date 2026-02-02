package me.faisal.smartext.modules.events

import org.koin.dsl.module

val eventBusModule = module {
    single { EventBus() }
}