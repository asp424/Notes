package com.lm.notes.di.dagger.reg.modules

import com.lm.notes.data.remote_data.firebase.FBAuth
import com.lm.notes.di.dagger.reg.RegScope
import dagger.Binds
import dagger.Module

@Module
interface FBAuthModule {

    @Binds
    @RegScope
    fun bindsFBAuth(fbAuth: FBAuth.Base): FBAuth
}