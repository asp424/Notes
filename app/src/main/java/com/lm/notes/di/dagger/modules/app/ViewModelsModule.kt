package com.lm.notes.di.dagger.modules.app

import com.lm.notes.di.dagger.annotations.AppScope
import com.lm.notes.presentation.ViewModels
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ViewModelsModule {

    @Binds
    @AppScope
    fun bindsViewModels(viewModels: ViewModels.Base): ViewModels
}