package com.lm.notes.ui.cells.view.app_widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.lm.notes.R
import javax.inject.Inject

interface NoteAppWidgetController {

    fun pinNoteWidget(notesText: Pair<String, String>)

    fun remoteViews(): RemoteViews

    class Base @Inject constructor(
        private val appWidgetManager: AppWidgetManager,
        private val componentName: ComponentName,
        private val pendingIntent: PendingIntent
    ) : NoteAppWidgetController {


        @RequiresApi(Build.VERSION_CODES.O)
        override fun pinNoteWidget(notesText: Pair<String, String>) {
            note = notesText
            appWidgetManager.requestPinAppWidget(componentName, null, pendingIntent)
        }

        override fun remoteViews() =
            RemoteViews(componentName.packageName, R.layout.note_widget).apply{
                setTextViewText(R.id.note_text, note.first)
                setTextViewText(R.id.note_header, note.second)
            }

        private var note = Pair("", "")
    }
}