package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.remote_data.firebase.ValueListener
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface ValueListenerModule {

    @Binds
    @AppScope
    fun bindsValueListener(valueListener: ValueListener.Base): ValueListener
}