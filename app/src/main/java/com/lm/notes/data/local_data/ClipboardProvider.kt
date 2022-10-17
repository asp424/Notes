package com.lm.notes.data.local_data

import android.content.ClipData
import android.content.ClipboardManager
import android.text.Spanned
import android.widget.EditText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.text.toHtml
import androidx.core.text.toSpanned
import com.lm.notes.R
import com.lm.notes.data.models.UiStates
import com.lm.notes.ui.cells.view.EditTextController
import com.lm.notes.ui.cells.view.app_widget.ToastCreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ClipboardProvider {

    val clipBoardIsNotEmpty: Boolean?

    fun ImageVector.clickOnButtonsClipboard()

    fun paste()

    fun copyAll()

    fun selectAll()

    fun copySelected()

    fun cutSelected()

    fun clearClipBoard()

    class Base @Inject constructor(
        private val clipboardManager: ClipboardManager,
        private val editTextController: EditTextController,
        private val uiStates: UiStates,
        private val toastCreator: ToastCreator
    ) : ClipboardProvider {

        override val clipBoardIsNotEmpty get() = readText.isNotEmpty()

        override fun paste() {
            with(editTextController.editText) {
                runCatching {
                    readText.trim().also { pasted ->
                        if (i == -1) text.append(pasted)
                        else text.replace(i, i1, pasted, 0, pasted.length)
                    }
                    with(uiStates) { false.setIsSelected }
                    editTextController.updateText()
                }
            }
        }

        override fun copyAll() {
            editTextController.editText.text.saveText
            with(uiStates) { clipBoardIsNotEmpty.setClipboardIsEmpty }
        }

        override fun selectAll() {
            with(editTextController) {
                with(editText) {
                    requestFocus()
                    setSelection(0, text.length)
                    setFormatMode()
                    setButtonColors()
                    saveSelection()
                }
            }
        }

        override fun copySelected() = with(editTextController.editText) {
            val oldText = text.toSpanned()
            text.replace(0, i, "").replace(i1, text.length, "").saveText
            setText(oldText)
            with(uiStates) {
                CoroutineScope(Dispatchers.Main).launch {
                    with(uiStates) {
                        editTextController.editText.clearFocus()
                        delay(200)
                        if (getIsFormatMode && getSetSelectionEnable) {
                            editTextController.setEditMode(); onClickEditText()
                        }
                    }
                }
                clipBoardIsNotEmpty.setClipboardIsEmpty
            }
        }

    override fun cutSelected() {
        with(editTextController.editText) {
            text.replace(0, i, "").replace(i1, text.length, "").saveText
        }
        with(uiStates) {
            with(editTextController) {
                if (getIsFormatMode) {
                    setEditMode(); onClickEditText(); removeSelection()
                    editText.clearFocus()
                }
                updateText()
                removeSelection()
            }
        }
    }

    override fun ImageVector.clickOnButtonsClipboard() =
        when (this) {
            Icons.Rounded.ContentPaste -> paste()
            Icons.Rounded.SelectAll -> selectAll()
            Icons.Rounded.ContentCopy -> {
                copySelected()
                toastCreator.invoke(R.string.selected_text_was_copied)
            }
            Icons.Rounded.CopyAll -> {
                copyAll()
                toastCreator.invoke(R.string.all_text_was_copied)
            }
            Icons.Rounded.ContentCut -> cutSelected()
            else -> Unit
        }

    override fun clearClipBoard() {
        "".toSpanned().saveText
        with(uiStates) { true.setClipboardIsEmpty }
    }

    private val Spanned.saveText
        get() = clipboardManager.setPrimaryClip(
            ClipData.newHtmlText("newText", this, toHtml())
        )

    private val readText
        get() = editTextController
            .fromHtml(clipboardManager.primaryClip?.getItemAt(0)?.htmlText ?: "")

    private val EditText.i get() = selectionStart.coerceAtMost(selectionEnd)

    private val EditText.i1 get() = selectionStart.coerceAtLeast(selectionEnd)
}
}
