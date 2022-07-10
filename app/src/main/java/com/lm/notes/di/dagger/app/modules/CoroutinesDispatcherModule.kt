package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.dagger.app.AppScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers.IO

@Module
class CoroutinesDispatcherModule {

    @Provides
    @AppScope
    fun providesCoroutinesDispatcher() = IO
}