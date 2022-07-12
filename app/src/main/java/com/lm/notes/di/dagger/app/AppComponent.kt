package com.lm.notes.di.dagger.app

import android.app.Application
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@[Component(modules = [AppMapModules::class]) AppScope]
interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun sPreferences(sPreferences: SPreferences): Builder

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun windowWidth(width: Float): Builder

        fun create(): AppComponent
    }

    fun inject(mainActivity: MainActivity)
}