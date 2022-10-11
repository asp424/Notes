package com.lm.notes.utils.format_text

import android.content.ClipData
import android.content.ClipboardManager
import android.widget.EditText
import androidx.compose.ui.text.AnnotatedString
import com.lm.notes.data.models.UiStates
import com.lm.notes.ui.cells.view.EditTextProvider
import com.lm.notes.ui.cells.view.SpansProvider
import javax.inject.Inject

interface ClipboardProvider {

    val clipBoardIsNotEmpty: Boolean?

    fun paste()

    fun copyAll()

    fun selectAll()

    fun copySelected()

    fun cutSelected()

    fun clearClipBoard()

    class Base @Inject constructor(
        private val clipboardManager: ClipboardManager,
        private val spansProvider: SpansProvider,
        private val uiStates: UiStates,
        private val editTextProvider: EditTextProvider
    ) : ClipboardProvider {

        override val clipBoardIsNotEmpty get() = readText?.isNotEmpty()

        override fun paste() {
            with(spansProvider.editText) {
                readText?.also { pasted -> text.replace(i, i1, pasted, 0, pasted.length) }
                with(uiStates) { false.setIsSelected }
                spansProvider.updateText(-1f)
            }
        }

        override fun copyAll() {
            AnnotatedString(spansProvider.editText.text.toString()).saveText
            with(uiStates) { clipBoardIsNotEmpty?.setClipboardIsEmpty }
        }

        override fun selectAll() {
            with(spansProvider) {
                editText.requestFocus()
                editText.setSelection(0, editText.text.length)
                uiStates.apply {
                    true.setIsSelected; true.setIsFormatMode; setAllButtonsWhite()
                }
                setFormatMode()
                setButtonColors()
                saveSelection()

            }
        }

        override fun copySelected() = with(spansProvider.editText) {
            AnnotatedString(text.substring(selectionStart, selectionEnd)).saveText
            with(uiStates) { clipBoardIsNotEmpty?.setClipboardIsEmpty ?: Unit }
        }

        override fun cutSelected() {
            with(spansProvider.editText) {
                AnnotatedString(text.substring(selectionStart, selectionEnd)).saveText
                text.replace(i, i1, "", 0, 0)
                with(uiStates) {
                    false.setIsFormatMode
                    clipBoardIsNotEmpty?.setClipboardIsEmpty
                    false.setIsSelected
                }
                spansProvider.updateText(-1f)
                editTextProvider.removeSelection()
            }
        }

        override fun clearClipBoard() {
            AnnotatedString("").saveText
            with(uiStates) { true.setClipboardIsEmpty }
        }

        private val AnnotatedString.saveText
            get() = clipboardManager.setPrimaryClip(ClipData.newPlainText("newText", this))

        private val readText
            get() = clipboardManager.primaryClip?.getItemAt(0)?.text?.trim()

        private val EditText.i get() = selectionStart.coerceAtMost(selectionEnd)

        private val EditText.i1 get() = selectionStart.coerceAtLeast(selectionEnd)
    }
}
