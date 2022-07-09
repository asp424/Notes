package com.lm.notes.di.dagger.reg.modules

import androidx.activity.ComponentActivity
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.di.dagger.reg.RegScope
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