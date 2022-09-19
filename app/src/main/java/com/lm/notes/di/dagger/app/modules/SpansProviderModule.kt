package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.ui.cells.view.SpansProvider
import dagger.Binds
import dagger.Module

@Module
interface SpansProviderModule {

    @Binds
    @AppScope
    fun bindsSpansProvider(spansProvider: SpansProvider.Base): SpansProvider
}