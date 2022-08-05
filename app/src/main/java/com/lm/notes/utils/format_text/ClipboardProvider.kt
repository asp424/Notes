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

    class Base @Inject constructor(
        private val clipboardManager: ClipboardManager,
        private val noteData: NoteData
    ) : ClipboardProvider {

        override fun clipBoardIsNotEmpty() = clipboardManager.getText()?.isNotEmpty()

        override fun paste() {
            textState.value = textState.value.copy(textState.value.text + readText)
        }

        override fun copyAll() = textState.value.annotatedString.saveText

        override fun selectAll() {
            textState.value = textState.value.copy(
                annotatedString = textState.value.annotatedString,
                selection = TextRange(0, textState.value.text.length)
            )
        }

        override fun copySelected() = textState.value.getSelectedText().saveText

        override fun cutSelected() = with(textState.value)
        {
            textState.value = TextFieldValue(text.replace(getSelectedText().text, ""))
            getSelectedText().saveText
        }

        private val AnnotatedString.saveText get() = clipboardManager.setText(this)

        private val readText get() = clipboardManager.getText()

        private val textState get() = noteData.noteModelFullScreen.value.textState
    }
}
