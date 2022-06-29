package com.lm.notes.di.dagger.modules.app

import androidx.lifecycle.ViewModel
import com.lm.notes.di.dagger.annotations.AppScope
import com.lm.notes.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
interface ViewModelModules {
    @IntoMap
    @Binds
    @AppScope
    @ViewModelKey(MainViewModel::class)
    fun bindsMainViewModel(viewModel: MainViewModel): ViewModel
}

