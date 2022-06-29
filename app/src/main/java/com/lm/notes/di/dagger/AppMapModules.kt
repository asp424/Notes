package com.lm.notes.di.dagger

import com.lm.notes.di.dagger.modules.app.ViewModelFactoryModule
import com.lm.notes.di.dagger.modules.app.ViewModelsModule
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Module(
    includes = [
        ViewModelFactoryModule::class,
        ViewModelsModule::class,
    ]
)
interface AppMapModules

