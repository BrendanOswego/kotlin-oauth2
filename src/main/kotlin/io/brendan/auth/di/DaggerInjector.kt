package io.brendan.auth.di

interface DaggerInjector<T> {
    fun inject(application: T)
}