package com.lm.notes.di.dagger.reg

import com.lm.notes.presentation.LoginActivity
import dagger.BindsInstance
import dagger.Component

@[Component(modules = [RegMapModules::class]) RegScope]
interface RegComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun loginActivity(loginActivity: LoginActivity): Builder

        fun create(): RegComponent
    }

    fun inject(loginActivity: LoginActivity)
}