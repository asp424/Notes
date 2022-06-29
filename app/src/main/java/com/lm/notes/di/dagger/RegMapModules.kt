package com.lm.notes.di.dagger

import com.lm.notes.di.dagger.modules.reg.FBAuthInstanceModule
import com.lm.notes.di.dagger.modules.reg.FBAuthModule
import com.lm.notes.di.dagger.modules.reg.OneTapGoogleAuthModule
import dagger.Module

@Module(
    includes = [
        FBAuthModule::class,
        OneTapGoogleAuthModule::class,
        FBAuthInstanceModule::class
    ]
)
interface RegMapModules
