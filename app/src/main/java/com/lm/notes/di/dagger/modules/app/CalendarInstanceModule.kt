package com.lm.notes.di.dagger.modules.app

import com.lm.notes.di.dagger.annotations.AppScope
import dagger.Module
import dagger.Provides
import java.util.Calendar

@Module
class CalendarInstanceModule {

    @Provides
    @AppScope
    fun providesCalendarInstance() = Calendar.getInstance()
}