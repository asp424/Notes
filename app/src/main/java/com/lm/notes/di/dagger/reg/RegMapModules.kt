package com.lm.notes.di.dagger.reg

import com.lm.notes.di.dagger.reg.modules.*
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
