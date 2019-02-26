package io.brendan.auth.di

import dagger.BindsInstance
import dagger.Component
import io.brendan.auth.AuthenticationApp
import org.jdbi.v3.core.Jdbi
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            ControllerModule::class
        ]
)
interface AppComponent : DaggerInjector<AuthenticationApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun database(db: Jdbi): Builder

        fun build(): AppComponent

    }

}