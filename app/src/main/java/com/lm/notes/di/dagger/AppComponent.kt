package com.lm.notes.di.dagger

import android.app.Application
import com.lm.notes.data.SPreferences
import com.lm.notes.di.dagger.annotations.AppScope
import com.lm.notes.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@[Component(modules = [AppMapModules::class]) AppScope]
interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun sPreferences(sPreferences: SPreferences): Builder

        @BindsInstance
        fun application(application: Application): Builder

        fun create(): AppComponent
    }

    fun inject(mainActivity: MainActivity)
}