package com.lm.notes.di.dagger.modules.app

import com.lm.notes.di.dagger.annotations.AppScope
import com.lm.notes.ui.BottomBar
import dagger.Binds
import dagger.Module

@Module
interface BottomBarModule {

    @Binds
    @AppScope
    fun bindsBottomBar(bottomBar: BottomBar.Base): BottomBar
}