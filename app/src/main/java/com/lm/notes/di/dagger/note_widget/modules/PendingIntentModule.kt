package com.lm.notes.di.dagger.note_widget.modules

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import com.lm.notes.di.dagger.app.NoteWidgetScope
import com.lm.notes.presentation.MainActivity
import dagger.Module
import dagger.Provides

@Module
    class PendingIntentModule {

        @Provides
        @NoteWidgetScope
        fun providesPendingIntent(application: Application)
                = PendingIntent.getBroadcast(
            application, 0, Intent(application, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE)
    }