package com.lm.notes.di.dagger.modules.app

import com.lm.notes.di.dagger.annotations.AppScope
import com.lm.notes.ui.MainColumn
import dagger.Binds
import dagger.Module

@Module
interface MainColumnModule {

    @Binds
    @AppScope
    fun bindsMainColumn(mainColumn: MainColumn.Base): MainColumn
}