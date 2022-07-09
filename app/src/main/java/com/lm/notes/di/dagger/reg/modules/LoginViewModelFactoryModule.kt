package com.lm.notes.di.dagger.reg.modules

import androidx.lifecycle.ViewModelProvider
import com.lm.notes.di.dagger.reg.RegScope
import com.lm.notes.presentation.ViewModelFactory
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module(includes = [LoginViewModelModule::class])
interface LoginViewModelFactoryModule {

    @Binds
    @RegScope
    fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

