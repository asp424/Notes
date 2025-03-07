package com.lm.notes.di.dagger.app

import android.app.Application
import android.widget.EditText
import com.lm.notes.presentation.MainActivity
import com.lm.notes.ui.cells.view.AccessibilityDelegate
import com.lm.notes.ui.cells.view.app_widget.NoteAppWidgetController
import com.lm.notes.ui.cells.view.app_widget.ToastCreator
import dagger.BindsInstance
import dagger.Component
import java.io.File

@[Component(modules = [AppMapModules::class]) AppScope]
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun toastCreator(toastCreator: ToastCreator): Builder

        @BindsInstance
        fun editText(editText: Function0<@JvmSuppressWildcards EditText>): Builder

        @BindsInstance
        fun filesDir(filesDir: File): Builder

        fun create(): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun noteAppWidgetController(): NoteAppWidgetController
    fun accessibilityDelegate(): AccessibilityDelegate
}