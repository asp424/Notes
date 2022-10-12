package com.lm.notes.di.dagger.note_widget

import android.app.Application
import android.widget.EditText
import com.lm.notes.di.dagger.app.AppComponent
import com.lm.notes.di.dagger.app.NoteWidgetScope
import com.lm.notes.ui.cells.view.app_widget.NoteAppWidget
import com.lm.notes.ui.cells.view.app_widget.NoteAppWidgetController
import dagger.BindsInstance
import dagger.Component
import java.io.File

@[Component(modules = [NoteWidgetMapModules::class]) NoteWidgetScope]
interface NoteWidgetComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun create(): NoteWidgetComponent
    }

    fun inject(noteAppWidget: NoteAppWidget)
    fun noteAppWidgetController(): NoteAppWidgetController
}