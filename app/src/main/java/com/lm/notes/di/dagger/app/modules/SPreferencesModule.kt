package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface SPreferencesModule {

    @Binds
    @AppScope
    fun bindsSPreferences(sPreferences: SPreferences.Base): SPreferences
}