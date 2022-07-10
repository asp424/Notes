package com.lm.notes.di.dagger.reg.modules

import com.lm.notes.data.remote_data.one_tap_google.OneTapGoogleAuth
import com.lm.notes.di.dagger.reg.RegScope
import dagger.Binds
import dagger.Module

@Module
interface OneTapGoogleAuthModule {

    @Binds
    @RegScope
    fun bindsOneTapGoogleAuth(oneTapGoogleAuth: OneTapGoogleAuth.Base): OneTapGoogleAuth
}