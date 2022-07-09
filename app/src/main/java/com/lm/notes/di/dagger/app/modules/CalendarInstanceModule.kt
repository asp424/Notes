package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.dagger.app.AppScope
import dagger.Module
import dagger.Provides
import java.util.Calendar

@Module
class CalendarInstanceModule {

    @Provides
    @AppScope
    fun providesCalendarInstance() = Calendar.getInstance()
}