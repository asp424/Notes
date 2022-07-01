package com.lm.notes.di.dagger.modules.app

import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.di.dagger.annotations.AppScope
import com.lm.notes.di.dagger.annotations.RegScope
import dagger.Module
import dagger.Provides

@Module
class FBAuthInstanceModule {

    @Provides
    @AppScope
    fun providesFBAuthInstance() = FirebaseAuth.getInstance()
}