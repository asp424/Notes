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

    fun showUnCompatibleToast()

    class Base @Inject constructor(
        private val appWidgetManager: AppWidgetManager,
        private val componentName: ComponentName,
        private val pendingIntent: PendingIntent,
        private val toastCreator: ToastCreator
    ) : NoteAppWidgetController {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun pinNoteWidget(notesText: Pair<String, String>) {
            note = notesText
            if (appWidgetManager.isRequestPinAppWidgetSupported)
            appWidgetManager.requestPinAppWidget(componentName, null, pendingIntent)
            else showUnCompatibleToast()
        }

        override fun remoteViews() =
            RemoteViews(componentName.packageName, R.layout.note_widget).apply{
                setTextViewText(R.id.note_text, note.first)
                setTextViewText(R.id.note_header, note.second)
            }

        override fun showUnCompatibleToast()
        = toastCreator.invoke(R.string.widget_not_compatible)

        private var note = Pair("", "")
    }
}

typealias ToastCreator = (Int) -> Unit