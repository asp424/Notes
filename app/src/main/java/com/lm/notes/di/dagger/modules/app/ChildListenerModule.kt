package com.lm.notes.di.dagger.modules.app

import com.lm.notes.data.remote_data.firebase.ChildListener
import com.lm.notes.di.dagger.annotations.AppScope
import dagger.Binds
import dagger.Module

@Module
interface ChildListenerModule {

    @Binds
    @AppScope
    fun bindsChildListener(childListenerModule: ChildListener.Base): ChildListener
}