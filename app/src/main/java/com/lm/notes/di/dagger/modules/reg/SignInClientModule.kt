package com.lm.notes.di.dagger.modules.reg

import com.google.android.gms.auth.api.identity.Identity
import com.lm.notes.di.dagger.annotations.RegScope
import com.lm.notes.presentation.LoginActivity
import dagger.Module
import dagger.Provides

@Module
class SignInClientModule {

    @Provides
    @RegScope
    fun providesSignInClient(loginActivity: LoginActivity) = Identity.getSignInClient(loginActivity)
}