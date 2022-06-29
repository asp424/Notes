package com.lm.notes.di.dagger.modules.reg

import com.lm.notes.data.remote_data.registration.FBAuth
import com.lm.notes.di.dagger.annotations.RegScope
import dagger.Binds
import dagger.Module

@Module
interface FBAuthModule {

    @Binds
    @RegScope
    fun bindsFBAuth(fbAuth: FBAuth.Base): FBAuth
}