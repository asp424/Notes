package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.rerositories.FirebaseRepository
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface FirebaseRepositoryModule {

    @Binds
    @AppScope
    fun bindsFirebaseRepository(firebaseRepository: FirebaseRepository.Base): FirebaseRepository
}