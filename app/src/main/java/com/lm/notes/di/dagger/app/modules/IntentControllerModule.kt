package com.lm.notes.di.dagger.app.modules

import com.lm.notes.core.IntentController
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface IntentControllerModule {

    @Binds
    @AppScope
    fun bindIntentController(intentController: IntentController.Base): IntentController
}