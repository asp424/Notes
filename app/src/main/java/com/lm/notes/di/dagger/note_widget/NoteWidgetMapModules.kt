package com.lm.notes.di.dagger.note_widget

import com.lm.notes.di.dagger.note_widget.modules.AppWidgetManagerModule
import com.lm.notes.di.dagger.note_widget.modules.ComponentNameModule
import com.lm.notes.di.dagger.note_widget.modules.NoteAppWidgetControllerModule
import com.lm.notes.di.dagger.note_widget.modules.PendingIntentModule
import dagger.Module

@Module(includes = [
    AppWidgetManagerModule::class,
    ComponentNameModule::class,
    PendingIntentModule::class,
    NoteAppWidgetControllerModule::class
])
interface NoteWidgetMapModules
