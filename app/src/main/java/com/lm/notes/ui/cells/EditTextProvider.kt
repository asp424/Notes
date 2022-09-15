package com.lm.notes.ui.cells

import android.os.IBinder
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.BackgroundColorSpan
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import com.lm.notes.data.local_data.NoteData
import javax.inject.Inject

interface EditTextProvider {

    val longClickState: Boolean

    val colorPickerIsShow: Boolean

    val editText: EditText

    var colorButton: Color

    var selection: Map<Int, Int>

    fun hideFormatPanel()

    fun showColorPicker()

    fun hideColorPicker()

    fun setText(text: String)
    fun toHtml(text: Spanned): String

    fun fromHtml(text: String): Spanned?

    fun setSpan(formatType: Any)

    fun isHaveBackgroundSpans(): Boolean

    fun removeBackgroundSpan()
    class Base @Inject constructor(
        private val noteData: NoteData,
        override val editText: EditText,
        private val inputMethodManager: InputMethodManager
    ) : EditTextProvider {

        override var longClickState by mutableStateOf(false)

        override var colorPickerIsShow by mutableStateOf(false)

        override var colorButton by mutableStateOf(Black)

        override var selection by mutableStateOf(mapOf<Int, Int>())

        init {
            editText.initEditText()
        }

        private val accessibilityDelegateInstance
            get() = object : View.AccessibilityDelegate() {

                override fun sendAccessibilityEvent(host: View?, eventType: Int) {
                    super.sendAccessibilityEvent(host, eventType)
                    if (eventType == 2) longClickState = true
                    if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED &&
                        longClickState
                    ) {
                        selection = mapOf(editText.selectionStart to editText.selectionEnd)
                        colorButton = Black
                        with(editText) {
                            backgroundSpansList.forEach { colorButton = Color(it.backgroundColor) }
                            editText.windowToken?.hideKeyboard
                            showSoftInputOnFocus = false
                            isCursorVisible = false
                            //if (isHaveBackgroundSpans()) hideColorPicker()
                        }
                    }
                }
            }

        override fun hideFormatPanel() {
            longClickState = false
            editText.showSoftInputOnFocus = true
            editText.isCursorVisible = true
        }

        override fun setText(text: String) = editText.setText(
            Html.fromHtml(text, FROM_HTML_MODE_LEGACY).trim()
        )

        private val EditText.backgroundSpansList
            get() = text.getSpans(selectionStart, selectionEnd, BackgroundColorSpan::class.java)

        private val EditText.onClickListener
            get() = setOnClickListener {
                if (longClickState) hideFormatPanel()
                hideColorPicker()
            }

        override fun isHaveBackgroundSpans() = with(editText) {
            backgroundSpansList.isNotEmpty()
        }

        override fun removeBackgroundSpan() = with(editText) {
            backgroundSpansList.forEach {
                text.removeSpan(it)
                colorButton = Black
                text.updateSpan
            }
        }

        override fun setSpan(formatType: Any) {
                if (formatType is BackgroundColorSpan) removeBackgroundSpan()
                editText.text.apply {
                    setSpan(formatType, editText.selectionStart, editText.selectionEnd, flagSpan)
                    updateSpan
                }
            }

        private val Spanned.updateSpan
            get() = noteData.noteModelFullScreen.value.also { noteModel ->
                noteModel.text = toHtml(this)
                noteModel.isChanged = true
            }

        override fun showColorPicker() { colorPickerIsShow = true }
        override fun hideColorPicker() { colorPickerIsShow = false }
        override fun toHtml(text: Spanned) = Html.toHtml(text, flagHtml).toString()
        override fun fromHtml(text: String): Spanned? = Html.fromHtml(text, flagHtml)
        private fun EditText.initEditText() {
            onClickListener
            customSelectionActionModeCallback = CallbackEditText()
            accessibilityDelegate = accessibilityDelegateInstance
        }

        private val flagHtml by lazy { FROM_HTML_MODE_LEGACY }

        private val flagSpan by lazy { SPAN_EXCLUSIVE_EXCLUSIVE }

        private val IBinder.hideKeyboard
            get() = inputMethodManager.hideSoftInputFromWindow(this, 0)
    }
}