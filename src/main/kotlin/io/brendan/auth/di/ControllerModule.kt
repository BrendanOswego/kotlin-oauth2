package io.brendan.auth.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import io.brendan.auth.controllers.*

@Module
interface ControllerModule {

    @Multibinds
    fun controllers(): Set<BaseController>

    @Binds
    @IntoSet
    fun bindIndexController(controller: IndexController): BaseController

    @Binds
    @IntoSet
    fun bindAuthorizeController(controller: AuthorizeController): BaseController

    @Binds
    @IntoSet
    fun bindRegisterController(controller: RegisterController): BaseController

    @Binds
    @IntoSet
    fun bindRedirectController(controller: RedirectController): BaseController

    @Binds
    @IntoSet
    fun bindTokenController(controller: TokenController): BaseController
}