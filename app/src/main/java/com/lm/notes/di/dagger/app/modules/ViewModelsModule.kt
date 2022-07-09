package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.presentation.ViewModels
import dagger.Binds
import dagger.Module

@Module
interface ViewModelsModule {

    @Binds
    @AppScope
    fun bindsViewModels(viewModels: ViewModels.Base): ViewModels
}