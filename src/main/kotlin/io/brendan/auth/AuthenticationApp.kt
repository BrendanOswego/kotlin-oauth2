package io.brendan.auth

import io.brendan.auth.di.DaggerAppComponent
import io.brendan.auth.di.DaggerApplication
import io.brendan.auth.di.DaggerInjector
import io.dropwizard.jdbi3.JdbiFactory
import io.dropwizard.setup.Environment

open class AuthenticationApp : DaggerApplication<Config>() {

    override fun injector(config: Config, environment: Environment)
            : DaggerInjector<out DaggerApplication<Config>> {

        val factory = JdbiFactory()
        val database = factory.build(environment, config.db, "postgresql")

        return DaggerAppComponent
                .builder()
                .database(database)
                .build()
    }
}

fun main(args: Array<String>) {
    AuthenticationApp().run(*args)
}