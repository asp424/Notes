package com.lm.notes.di.dagger.app

import android.app.Application
import android.widget.EditText
import com.lm.notes.data.local_data.IntentBorn
import com.lm.notes.presentation.MainActivity
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
        fun editText(editText: EditText): Builder

        @BindsInstance
        fun intentBuilder(intentBuilder: IntentBorn): Builder

        @BindsInstance
        fun filesDir(filesDir: File): Builder

        fun create(): AppComponent
    }

    fun inject(mainActivity: MainActivity)
}