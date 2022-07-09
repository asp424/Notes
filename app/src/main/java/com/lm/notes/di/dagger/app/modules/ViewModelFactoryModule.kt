package com.lm.notes.di.dagger.app.modules

import androidx.lifecycle.ViewModelProvider
import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.presentation.ViewModelFactory
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module(includes = [ViewModelModules::class])
interface ViewModelFactoryModule {

    @Binds
    @AppScope
    fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

