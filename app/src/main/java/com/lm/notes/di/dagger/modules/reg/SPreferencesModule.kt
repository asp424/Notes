package com.lm.notes.di.dagger.modules.reg

import androidx.activity.ComponentActivity
import com.lm.notes.data.SPreferences
import com.lm.notes.di.dagger.annotations.RegScope
import com.lm.notes.presentation.LoginActivity
import dagger.Module
import dagger.Provides

@Module
class SPreferencesModule {

    @Provides
    @RegScope
    fun providesSPreferences(loginActivity: LoginActivity): SPreferences =
        SPreferences.Base(
            loginActivity.getSharedPreferences("user", ComponentActivity.MODE_PRIVATE)
        )
}