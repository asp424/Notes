package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.remote_data.firebase.FirebaseRepository
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface FirebaseRepositoryModule {

    @Binds
    @AppScope
    fun bindsFirebaseRepository(firebaseRepository: FirebaseRepository.Base): FirebaseRepository
}