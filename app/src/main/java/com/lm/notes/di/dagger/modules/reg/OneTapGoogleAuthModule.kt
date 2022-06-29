package com.lm.notes.di.dagger.modules.reg

import com.lm.notes.data.remote_data.registration.OneTapGoogleAuth
import com.lm.notes.di.dagger.annotations.RegScope
import dagger.Binds
import dagger.Module

@Module
interface OneTapGoogleAuthModule {

    @Binds
    @RegScope
    fun bindsOneTapGoogleAuth(oneTapGoogleAuth: OneTapGoogleAuth.Base): OneTapGoogleAuth
}