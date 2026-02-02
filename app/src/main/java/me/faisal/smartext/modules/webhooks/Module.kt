package me.faisal.smartext.modules.webhooks

import me.faisal.smartext.modules.webhooks.db.WebhookQueueRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val webhooksModule = module {
    singleOf(::WebHooksService)
    singleOf(::WebhookQueueRepository)
}

val NAME = "webhooks"