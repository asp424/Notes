package com.lm.notes.di.dagger.app.modules

import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.di.dagger.app.AppScope
import dagger.Module
import dagger.Provides

@Module
class FBAuthInstanceModule {

    @Provides
    @AppScope
    fun providesFBAuthInstance() = FirebaseAuth.getInstance()
}