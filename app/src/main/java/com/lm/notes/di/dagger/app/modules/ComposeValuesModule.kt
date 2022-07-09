package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.compose.ComposeValues
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface ComposeValuesModule {

    @Binds
    @AppScope
    fun bindsComposeValues(composeValues: ComposeValues.Base): ComposeValues
}