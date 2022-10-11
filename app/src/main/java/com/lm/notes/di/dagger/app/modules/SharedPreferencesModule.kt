package com.lm.notes.di.dagger.app.modules

import android.app.Application
import android.content.SharedPreferences
import com.lm.notes.di.dagger.app.AppScope
import dagger.Module
import dagger.Provides

@Module
class SharedPreferencesModule {

    @Provides
    @AppScope
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("user", Application.MODE_PRIVATE)

}