package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.remote_data.firebase.FirebaseHandler
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface FirebaseHandlerModule {

    @Binds
    @AppScope
    fun bindsFirebaseHandler(firebaseHandler: FirebaseHandler.Base): FirebaseHandler
}