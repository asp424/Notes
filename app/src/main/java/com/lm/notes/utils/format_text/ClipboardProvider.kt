package com.lm.notes.utils.format_text

import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import com.lm.notes.data.local_data.NoteData
import javax.inject.Inject

interface ClipboardProvider {

    fun clipBoardIsNotEmpty(): Boolean?

    fun paste()

    fun copyAll()

    fun selectAll()

    fun copySelected()

    fun cutSelected()

    fun clearClipBoard()

    class Base @Inject constructor(
        private val clipboardManager: ClipboardManager,
        private val noteData: NoteData
    ) : ClipboardProvider {

        override fun clipBoardIsNotEmpty() = clipboardManager.getText()?.isNotEmpty()

        override fun paste() {
        }

        override fun copyAll() = Unit

        override fun selectAll() {

        }

        override fun copySelected() = Unit

        override fun cutSelected() = with(text)
        {

        }

        override fun clearClipBoard() {
            AnnotatedString("").saveText
        }

        private val AnnotatedString.saveText get() = clipboardManager.setText(this)

        private val readText get() = clipboardManager.getText()

        private val text get() = noteData.noteModelFullScreen.value.text
    }
}
