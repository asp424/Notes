package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.ui.TopBar
import dagger.Binds
import dagger.Module

@Module
interface TopBarModule {

    @Binds
    @AppScope
    fun bindsTopBar(topBar: TopBar.Base): TopBar
}