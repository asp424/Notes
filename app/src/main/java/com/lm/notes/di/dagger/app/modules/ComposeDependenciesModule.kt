package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.compose.ComposeDependencies
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface ComposeDependenciesModule {

    @Binds
    @AppScope
    fun bindsComposeDependencies(composeDependencies: ComposeDependencies.Base)
            : ComposeDependencies
}