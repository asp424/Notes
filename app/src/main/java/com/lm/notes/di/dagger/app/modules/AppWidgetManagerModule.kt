package com.lm.notes.di.dagger.app.modules

import android.app.Application
import android.appwidget.AppWidgetManager
import com.lm.notes.di.dagger.app.AppScope
import dagger.Module
import dagger.Provides

@Module
class AppWidgetManagerModule {

    @Provides
    @AppScope
    fun providesAppWidgetManager(application: Application)
    = AppWidgetManager.getInstance(application)
}