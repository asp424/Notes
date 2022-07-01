package com.lm.notes.di.dagger

import com.lm.notes.di.dagger.modules.app.*
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Module(
    includes = [
        ViewModelFactoryModule::class,
        ViewModelsModule::class,
        ScreensModule::class,
        ComposeDependenciesModule::class,
        ComposeValuesModule::class,
        FBAuthInstanceModule::class
    ]
)
interface AppMapModules

