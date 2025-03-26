package com.lm.notes.data.local_data

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.text.toHtml
import androidx.core.text.toSpanned
import com.lm.notes.R
import com.lm.notes.data.models.UiStates
import com.lm.notes.ui.cells.view.EditTextController
import com.lm.notes.ui.cells.view.app_widget.ToastCreator
import javax.inject.Inject

interface ClipboardProvider {

    fun ImageVector.clickOnButtonsClipboard()

    fun paste()

    fun copyAll()

    fun selectAll()

    fun copySelected()

    fun cutSelected()

    fun clearClipBoard()

    fun contentDescription(): String

    fun addListener()

    fun removeListener()

    fun checkForEmpty()

    @RequiresApi(Build.VERSION_CODES.O)
    class Base @Inject constructor(
        private val clipboardManager: ClipboardManager,
        private val editTextController: EditTextController,
        private val uiStates: UiStates,
        private val toastCreator: ToastCreator
    ) : ClipboardProvider {

        private val listener by lazy {
            ClipboardManager.OnPrimaryClipChangedListener { checkForEmpty() }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun checkForEmpty() {
            with(uiStates) {
                with(hasClip()) {
                    if (this) contentDescription().setPasteIconLabel; setClipboardIsEmpty
                }
            }
        }

        override fun addListener() {
            clipboardManager.addPrimaryClipChangedListener(listener)
        }

        override fun removeListener() {
            clipboardManager.removePrimaryClipChangedListener(listener)
        }

        override fun paste() = with(uiStates) {
            if (hasClip()) clipboardManager.primaryClip?.getItemAt(0)?.apply {
                htmlText?.apply { removeSelected(this) } ?: removeSelected(text ?: "")
                checkForEmpty()
                false.setIsSelected
            }
        }

        override fun copyAll() = editTextController.editText.text.saveText

        override fun selectAll() = with(editTextController) {
            editText.requestFocus()
            editText.setText(editText.text.trim())
            editText.setSelection(0, editText.text.length)
            setFormatMode()
            setButtonColors()
            saveSelection()
        }

        override fun copySelected() = with(editTextController.editText) {
            SpannableStringBuilder(
                text.subSequence(selectionStart, selectionEnd).trim()
            ).saveText
        }

        override fun cutSelected() {
            copySelected()
            removeSelected()
        }

        private fun removeSelected(new: Any = "") = with(editTextController.editText) {
                text.replace(selectionStart, selectionEnd, if (new is String)
                        editTextController.fromHtml(new).trim()
                    else (new as CharSequence).toSpanned().trim()
                )
                editTextController.updateText()
            }

        @RequiresApi(Build.VERSION_CODES.P)
        override fun ImageVector.clickOnButtonsClipboard() =
            when (this) {
                Icons.Rounded.ContentPaste -> paste()
                Icons.Rounded.ClearAll -> clearClipBoard()
                Icons.Rounded.SelectAll -> selectAll()
                Icons.Rounded.ContentCopy -> {
                    copySelected()
                    toastCreator(R.string.selected_text_was_copied)
                }

                Icons.Rounded.CopyAll -> {
                    copyAll()
                    toastCreator(R.string.all_text_was_copied)
                }

                Icons.Rounded.ContentCut -> cutSelected()

                Icons.Rounded.Delete -> removeSelected()

                else -> Unit
            }

        @RequiresApi(Build.VERSION_CODES.P)
        override fun clearClipBoard() = clipboardManager.clearPrimaryClip()

        @RequiresApi(Build.VERSION_CODES.O)
        override fun contentDescription() =
            clipboardManager.primaryClipDescription?.label.toString()

        private val Spanned.saveText
            get() = clipboardManager.setPrimaryClip(
                ClipData.newHtmlText("html", trim(), toHtml())
            )

        private fun hasClip() = clipboardManager.hasPrimaryClip()
    }
}
