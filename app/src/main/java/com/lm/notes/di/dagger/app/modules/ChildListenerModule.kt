package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.remote_data.firebase.ChildListener
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface ChildListenerModule {

    @Binds
    @AppScope
    fun bindsChildListener(childListenerModule: ChildListener.Base): ChildListener
}