package com.lm.notes.di.dagger.app.modules

import com.lm.notes.core.Shortcuts
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface ShortcutsModule {

    @Binds
    @AppScope
    fun bindsShortcuts(shortcuts: Shortcuts.Base): Shortcuts
}