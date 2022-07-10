package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.sources.FirebaseSource
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface FirebaseSourceModule {

    @Binds
    @AppScope
    fun bindsFirebaseSource(firebaseSource: FirebaseSource.Base): FirebaseSource
}