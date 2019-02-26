package io.brendan.auth.di

import io.brendan.auth.controllers.BaseController
import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.setup.Environment
import org.slf4j.LoggerFactory
import javax.inject.Inject

abstract class DaggerApplication<T : Configuration> : Application<T>() {

    private val logger = LoggerFactory.getLogger(DaggerApplication::class.java)

    @Inject
    @JvmSuppressWildcards
    internal lateinit var controllers: Set<BaseController>

    override fun run(config: T, environment: Environment) {
        (injector(config, environment) as DaggerInjector<DaggerApplication<T>>).inject(this)

        controllers.forEach { controller ->
            logger.info("registered controller -> ${controller::class.java.simpleName}")
            environment.jersey().register(controller)
        }

    }

    abstract fun injector(config: T, environment: Environment)
            : DaggerInjector<out DaggerApplication<*>>

}