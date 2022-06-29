package com.lm.notes.di.dagger

import com.lm.notes.di.dagger.annotations.AppScope
import com.lm.notes.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@[Component(modules = [AppMapModules::class]) AppScope]
interface AppComponent {


    fun inject(mainActivity: MainActivity)
}