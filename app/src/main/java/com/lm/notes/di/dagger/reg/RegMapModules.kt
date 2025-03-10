package com.lm.notes.di.dagger.reg

import com.lm.notes.di.dagger.reg.modules.FBAuthInstanceRegModule
import com.lm.notes.di.dagger.reg.modules.FBAuthModule
import com.lm.notes.di.dagger.reg.modules.LoginViewModelFactoryModule
import com.lm.notes.di.dagger.reg.modules.LoginViewModelModule
import com.lm.notes.di.dagger.reg.modules.OneTapGoogleAuthModule
import com.lm.notes.di.dagger.reg.modules.SPreferencesModule
import com.lm.notes.di.dagger.reg.modules.SignInClientModule
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Module(
    includes = [
        FBAuthModule::class,
        OneTapGoogleAuthModule::class,
        SignInClientModule::class,
        LoginViewModelFactoryModule::class,
        LoginViewModelModule::class,
        SPreferencesModule::class,
        FBAuthInstanceRegModule::class
    ]
)
interface RegMapModules
