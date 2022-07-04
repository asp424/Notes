package com.lm.notes.di.dagger.modules.app

import com.lm.notes.data.remote_data.firebase.FirebaseRepository
import com.lm.notes.di.dagger.annotations.AppScope
import dagger.Binds
import dagger.Module

@Module
interface FirebaseRepositoryModule {

    @Binds
    @AppScope
    fun bindsFirebaseRepository(firebaseRepository: FirebaseRepository.Base): FirebaseRepository
}