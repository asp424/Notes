package com.lm.notes.di.dagger.modules.reg

import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.di.dagger.annotations.RegScope
import dagger.Module
import dagger.Provides

@Module
class FBAuthInstanceModule {

    @Provides
    @RegScope
    fun providesFBAuthInstance() = FirebaseAuth.getInstance()
}