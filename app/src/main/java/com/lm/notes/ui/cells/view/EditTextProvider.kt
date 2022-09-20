package com.lm.notes.ui.cells.view

import android.os.IBinder
import android.text.Html.*
import android.widget.EditText
import com.lm.notes.data.models.UiStates
import javax.inject.Inject

interface EditTextProvider {

    fun setText(text: String)

    fun setEditMode()

    class Base @Inject constructor(
        private val editText: EditText,
        private val uiStates: UiStates,
        private val accessibilityDelegateIns: AccessibilityDelegate
    ) : EditTextProvider {

        init { editText.initEditText() }

        override fun setText(text: String) = editText.setText(fromHtml(text, htmlMode).trim())
        override fun setEditMode() =
            with(editText) { showSoftInputOnFocus = true; isCursorVisible = true }

        private fun EditText.initEditText() {
            setOnClickListener { uiStates.onClickEditText(); setEditMode() }
            customSelectionActionModeCallback = CallbackEditText()
            // customInsertionActionModeCallback = this
            accessibilityDelegate = accessibilityDelegateIns
        }

        private val htmlMode by lazy { FROM_HTML_MODE_LEGACY }
    }
}