package com.lm.notes.core

import android.app.Application
import android.content.Context
import com.lm.notes.di.dagger.app.AppComponent
import com.lm.notes.di.dagger.app.DaggerAppComponent
import com.lm.notes.di.dagger.note_widget.DaggerNoteWidgetComponent
import com.lm.notes.di.dagger.note_widget.NoteWidgetComponent

class App : Application() {

    val appComponentBuilder: AppComponent.Builder by lazy {
        DaggerAppComponent.builder().application(this@App).filesDir(filesDir)
    }
    val noteWidgetComponent by lazy { DaggerNoteWidgetComponent.builder().application(this).create() }
}

val Context.appComponentBuilder: AppComponent.Builder
    get() = when (this) {
        is App -> appComponentBuilder
        else -> (applicationContext as App).appComponentBuilder
    }

val Context.noteWidgetComponent: NoteWidgetComponent
    get() = when (this) {
        is App -> noteWidgetComponent
        else -> (applicationContext as App).noteWidgetComponent
    }