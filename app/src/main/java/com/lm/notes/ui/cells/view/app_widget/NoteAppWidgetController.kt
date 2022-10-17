package com.lm.notes.ui.cells.view.app_widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Build
import android.text.Spanned
import android.widget.RemoteViews
import com.lm.notes.R
import com.lm.notes.data.local_data.NoteData
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.data.local_data.room.NoteModelRoom
import com.lm.notes.data.rerositories.RoomRepository
import com.lm.notes.ui.cells.view.EditTextController
import com.lm.notes.utils.getHeader
import javax.inject.Inject


interface NoteAppWidgetController {

    fun pinNoteWidget(noteId: String)

    suspend fun setUpTextToWidget(id: String): RemoteViews

    fun showUnCompatibleToast()

    suspend fun setStrikeThroughSpan(id: String)

    class Base @Inject constructor(
        private val appWidgetManager: AppWidgetManager,
        private val componentName: ComponentName,
        private val pendingIntent: PendingIntent,
        private val toastCreator: ToastCreator,
        private val roomRepository: RoomRepository,
        private val editTextController: EditTextController,
        private val sPreferences: SPreferences,
        private val noteData: NoteData
    ) : NoteAppWidgetController {

        override fun pinNoteWidget(noteId: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                && appWidgetManager.isRequestPinAppWidgetSupported
            ) {
                sPreferences.setPinnedNoteId(noteId)
                appWidgetManager.requestPinAppWidget(componentName, null, pendingIntent)
            } else showUnCompatibleToast()
        }

        override suspend fun setUpTextToWidget(id: String) = remoteViews.apply {
            roomRepository.getNote(sPreferences.getPinnedNoteId())?.also {
                setText(editTextController.fromHtml(it.text))
                setHeader(it.header.getHeader)
                sPreferences.setNoteId(id)
            }
        }

        override suspend fun setStrikeThroughSpan(id: String) = with(remoteViews) {
            val noteModelRoom =
                roomRepository.getNote(sPreferences.getNoteId(id)) ?: NoteModelRoom()
            setText(editTextController.fromHtml(noteModelRoom.text.strikeThroughSpan))
            setHeader(noteModelRoom.header.getHeader)
            appWidgetManager.updateAppWidget(id.toInt(), this)
        }

        override fun showUnCompatibleToast() =
            toastCreator.invoke(R.string.widget_not_compatible)

        private val remoteViews by lazy {
            RemoteViews(componentName.packageName, R.layout.note_widget)
        }

        private fun RemoteViews.setText(text: Spanned) = setTextViewText(R.id.note_text, text)

        private fun RemoteViews.setHeader(header: String) =
            setTextViewText(R.id.note_header, header)

        private val String.getHeader get() = getHeader(noteData.isNewHeader(this))

        private val String.strikeThroughSpan get() = "<strike>$this</strike>"
    }
}

typealias ToastCreator = (Int) -> Unit