package com.lm.notes.di.dagger.note_widget

import com.lm.notes.di.dagger.note_widget.modules.*
import dagger.Module

@Module(includes = [
    AppWidgetManagerModule::class,
    ComponentNameModule::class,
    PendingIntentModule::class,
    NoteAppWidgetControllerModule::class
])
interface NoteWidgetMapModules
