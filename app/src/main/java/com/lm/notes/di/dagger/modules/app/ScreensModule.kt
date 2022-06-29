package com.lm.notes.di.dagger.modules.app

import com.lm.notes.di.dagger.annotations.AppScope
import com.lm.notes.ui.Screens
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ScreensModule {

    @Binds
    @AppScope
    fun bindsScreens(screens: Screens.Base): Screens
}