package com.lm.notes.di.dagger.modules.app

import com.lm.notes.di.compose.ComposeDependencies
import com.lm.notes.di.dagger.annotations.AppScope
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ComposeDependenciesModule {

    @Binds
    @AppScope
    fun bindsComposeDependencies(composeDependencies: ComposeDependencies.Base)
            : ComposeDependencies
}