package com.lm.notes.ui.cells.view

import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Html.fromHtml
import android.widget.EditText
import com.lm.notes.data.local_data.NoteData
import com.lm.notes.data.models.UiStates
import javax.inject.Inject

interface EditTextProvider {

    fun setText(text: String)

    fun setEditMode()

    fun removeSelection()

    class Base @Inject constructor(
        private val editText: EditText,
        private val uiStates: UiStates,
        private val accessibilityDelegateIns: AccessibilityDelegate,
        private val noteData: NoteData
    ) : EditTextProvider {

        init {
            editText.initEditText()
        }

        override fun setText(text: String) {
            editText.setText(
                fromHtml(
                    text, htmlMode, null, MyHtmlTagHandler(
                        noteData.noteModelFullScreen.value.textScaleState
                    )
                ).trim()
            )
        }

        override fun setEditMode() =
            with(editText) {
                showSoftInputOnFocus = true; isCursorVisible = true; removeSelection()
            }

        private fun EditText.initEditText() {
            setOnClickListener { uiStates.onClickEditText(); setEditMode() }
            CallbackEditText().also {
                customSelectionActionModeCallback = it
                customInsertionActionModeCallback = it
            }
            accessibilityDelegate = accessibilityDelegateIns
        }

        override fun removeSelection() = with(editText) {
            with(uiStates) {
                if (getSelection != Pair(0, 0)) {
                    setSelection(editText.text.length, editText.text.length)
                    Pair(0, 0).setSelection
                    clearFocus()
                }
            }
        }

        private val htmlMode by lazy { FROM_HTML_MODE_LEGACY }
    }
}