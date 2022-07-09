package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.ui.Screens
import dagger.Binds
import dagger.Module

@Module
interface ScreensModule {

    @Binds
    @AppScope
    fun bindsScreens(screens: Screens.Base): Screens
}