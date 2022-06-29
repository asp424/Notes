package com.lm.notes.di.dagger

import com.google.android.gms.auth.api.identity.SignInClient
import com.lm.notes.data.remote_data.registration.FBAuth
import com.lm.notes.data.remote_data.registration.OneTapGoogleAuth
import com.lm.notes.di.dagger.annotations.RegScope
import dagger.BindsInstance
import dagger.Component
import dagger.Component.Builder

@[Component(modules = [RegMapModules::class]) RegScope]
interface RegComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun signInClient(signInClient: SignInClient): Builder

        fun create(): RegComponent
    }

    fun fBAuth(): FBAuth

    fun oTGAuth(): OneTapGoogleAuth
}