package com.lm.notes.ui.cells.view

import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.widget.EditText
import com.lm.notes.data.models.NoteModel
import com.lm.notes.data.models.UiStates
import javax.inject.Inject

interface EditTextProvider {

    val editText: EditText

    fun hideFormatPanel()

    fun setText(noteModel: NoteModel)

    class Base @Inject constructor(
        override val editText: EditText,
        private val uiStates: UiStates,
        private val accessibilityDelegateIns: AccessibilityDelegate
    ) : EditTextProvider {

        init { editText.initEditText() }

        override fun hideFormatPanel() {
            with(uiStates) { false.setLongClickState }
            with(editText) { showSoftInputOnFocus = true; isCursorVisible = true }
        }

        override fun setText(noteModel: NoteModel) = with(noteModel) {
            editText.setText(Html.fromHtml(text, FROM_HTML_MODE_LEGACY).trim())
        }

        private fun EditText.initEditText() {
            setOnClickListener {
                with(uiStates) {
                    if (getLongClickState) hideFormatPanel()
                    false.setAllColorPickerIsShow
                }
            }
            customSelectionActionModeCallback = CallbackEditText()
            // customInsertionActionModeCallback = this
            accessibilityDelegate = accessibilityDelegateIns
        }
    }
}