package com.lm.notes.di.dagger.modules.app

import com.lm.notes.di.compose.ComposeValues
import com.lm.notes.di.dagger.annotations.AppScope
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ComposeValuesModule {

    @Binds
    @AppScope
    fun bindsComposeValues(composeValues: ComposeValues.Base): ComposeValues
}