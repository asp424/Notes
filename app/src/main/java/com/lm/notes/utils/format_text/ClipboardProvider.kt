package com.lm.notes.utils.format_text

import android.content.ClipData
import android.content.ClipboardManager
import android.widget.EditText
import androidx.compose.ui.text.AnnotatedString
import com.lm.notes.data.models.UiStates
import com.lm.notes.ui.cells.view.EditTextController
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
        private val editTextController: EditTextController,
        private val uiStates: UiStates
    ) : ClipboardProvider {

        override val clipBoardIsNotEmpty get() = readText?.isNotEmpty()

        override fun paste() {
            with(editTextController.editText) {
                readText?.also { pasted ->
                    if (i == -1) text.append(pasted)
                    else text.replace(i, i1, pasted, 0, pasted.length)
                }
                with(uiStates) { false.setIsSelected }
                editTextController.updateText()
            }
        }

        override fun copyAll() {
            AnnotatedString(editTextController.editText.text.toString()).saveText
            with(uiStates) { clipBoardIsNotEmpty?.setClipboardIsEmpty }
        }

        override fun selectAll() {
            with(editTextController) {
                editText.requestFocus()
                editText.setSelection(0, editText.text.length)
                setFormatMode()
                setButtonColors()
                saveSelection()
            }
        }

        override fun copySelected() = with(editTextController.editText) {
            AnnotatedString(text.substring(selectionStart, selectionEnd)).saveText
            with(uiStates) { clipBoardIsNotEmpty?.setClipboardIsEmpty ?: Unit }
        }

        override fun cutSelected() {
            with(editTextController.editText) {
                AnnotatedString(text.substring(selectionStart, selectionEnd)).saveText
                text.replace(i, i1, "", 0, 0)
                with(uiStates) {
                    false.setIsFormatMode
                    clipBoardIsNotEmpty?.setClipboardIsEmpty
                    false.setIsSelected
                }
                editTextController.updateText()
                editTextController.removeSelection()
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
