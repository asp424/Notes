package com.lm.notes.di.dagger.reg.modules

import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.di.dagger.reg.RegScope
import dagger.Module
import dagger.Provides

@Module
class FBAuthInstanceRegModule {

    @Provides
    @RegScope
    fun providesFBAuthInstance() = FirebaseAuth.getInstance()
}