package com.lm.notes.di.dagger.app.modules

import android.app.Application
import android.content.ComponentName
import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.ui.cells.view.app_widget.NoteAppWidget
import dagger.Module
import dagger.Provides

@Module
class ComponentNameModule {

    @Provides
    @AppScope
    fun providesComponentName(application: Application)
            = ComponentName(application, NoteAppWidget::class.java)
}