package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.models.UiStates
import com.lm.notes.di.dagger.app.AppScope
import dagger.Module
import dagger.Provides

@Module
class UiStatesModule {

    @Provides
    @AppScope
    fun provideUiStates() = UiStates()
}