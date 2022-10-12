package com.lm.notes.di.dagger.note_widget.modules

import android.app.Application
import android.appwidget.AppWidgetManager
import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.di.dagger.app.NoteWidgetScope
import dagger.Module
import dagger.Provides

@Module
class AppWidgetManagerModule {

    @Provides
    @NoteWidgetScope
    fun providesAppWidgetManager(application: Application)
    = AppWidgetManager.getInstance(application)
}