package com.lm.notes.di.dagger.note_widget.modules

import android.app.Application
import android.content.ComponentName
import com.lm.notes.di.dagger.app.NoteWidgetScope
import com.lm.notes.ui.cells.view.app_widget.NoteAppWidget
import dagger.Module
import dagger.Provides

@Module
class ComponentNameModule {

    @Provides
    @NoteWidgetScope
    fun providesComponentName(application: Application)
            = ComponentName(application, NoteAppWidget::class.java)
}