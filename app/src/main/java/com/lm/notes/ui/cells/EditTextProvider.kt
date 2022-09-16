package com.lm.notes.ui.cells

import android.os.IBinder
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.core.text.set
import com.lm.notes.data.local_data.NoteData
import javax.inject.Inject

interface EditTextProvider {

    val longClickState: Boolean

    val colorPickerBackgroundIsShow: Boolean

    val colorPickerForegroundIsShow: Boolean

    var colorPickerUnderlinedIsShow: Boolean

    val editText: EditText

    var colorButtonBackground: Color

    var colorButtonForeground: Color

    var colorButtonUnderlined: Color

    var selection: Map<Int, Int>

    fun hideFormatPanel()
    fun showColorPickerBackground()
    fun showColorPickerForeground()
    fun showColorPickerUnderlined()
    fun hideColorPickerBackground()
    fun hideColorPickerForeground()
    fun hideColorPickerUnderlined()
    fun setText(text: String)
    fun toHtml(text: Spanned): String
    fun fromHtml(text: String): Spanned?
    fun <T> setSpan(typeSpan: T)
    fun <T> isHaveSpans(clazz: Class<T>): Boolean
    fun removeBackgroundSpan()
    fun removeUnderlinedSpan()
    fun removeForegroundSpan()
    class Base @Inject constructor(
        private val noteData: NoteData,
        override val editText: EditText,
        private val inputMethodManager: InputMethodManager
    ) : EditTextProvider {

        override var longClickState by mutableStateOf(false)

        override var colorPickerBackgroundIsShow by mutableStateOf(false)

        override var colorPickerForegroundIsShow by mutableStateOf(false)

        override var colorPickerUnderlinedIsShow by mutableStateOf(false)

        override var colorButtonBackground by mutableStateOf(Black)

        override var colorButtonForeground by mutableStateOf(Black)

        override var colorButtonUnderlined by mutableStateOf(Black)

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
                        colorButtonBackground = Black
                        colorButtonForeground = Black
                        colorButtonUnderlined = Black
                        with(editText) {
                            with(listSpans(BackgroundColorSpan::class.java)) {
                                if (isNotEmpty()) colorButtonBackground =
                                    get(0).backgroundColor.getColor
                            }
                            with(listSpans(ForegroundColorSpan::class.java)) {
                                if (isNotEmpty()) colorButtonForeground =
                                    get(0).foregroundColor.getColor
                            }

                            with(listSpans(ColoredUnderlineSpan::class.java)) {
                                if (isNotEmpty()) colorButtonUnderlined = get(0).underlineColor.getColor
                            }
                            windowToken?.hideKeyboard
                            showSoftInputOnFocus = false
                        }
                    }
                }
            }

        override fun hideFormatPanel() {
            longClickState = false
            with(editText) {
                showSoftInputOnFocus = true
                isCursorVisible = true
            }
        }

        override fun setText(text: String) = editText.setText(
            Html.fromHtml(text, FROM_HTML_MODE_LEGACY).trim()
        )

        private val EditText.onClickListener
            get() = setOnClickListener {
                if (longClickState) hideFormatPanel()
                hideColorPickerBackground()
                hideColorPickerForeground()
                hideColorPickerUnderlined()
            }

        override fun removeBackgroundSpan() = with(editText) {
            listSpans(BackgroundColorSpan::class.java).forEach {
                setSpansAroundSelected(text.getSpanStart(it), text.getSpanEnd(it))
                { BackgroundColorSpan(it.backgroundColor) }
                text.removeSpan(it)
            }
        }.apply { editText.text.updateSpan }

        override fun removeUnderlinedSpan() = with(editText) {
            listSpans(ColoredUnderlineSpan::class.java).forEach {
                setSpansAroundSelected(text.getSpanStart(it), text.getSpanEnd(it))
                { ColoredUnderlineSpan(android.graphics.Color.RED, 10f) }
                text.removeSpan(it)
            }
        }.apply { editText.text.updateSpan }

        override fun removeForegroundSpan() = with(editText) {
            listSpans(ForegroundColorSpan::class.java).forEach {
                setSpansAroundSelected(text.getSpanStart(it), text.getSpanEnd(it))
                { ForegroundColorSpan(it.foregroundColor) }
                text.removeSpan(it)
            }
        }.apply { editText.text.updateSpan }

        private fun <T : Any> EditText.setSpansAroundSelected(start: Int, end: Int, span: () -> T) {
            if (start < selectionStart) text[start..selectionStart] = span.invoke()
            if (end > selectionEnd) text[selectionEnd..end] = span.invoke()
        }

        override fun <T> isHaveSpans(clazz: Class<T>) = listSpans(clazz).isNotEmpty()
        private fun <T> listSpans(clazz: Class<T>): Array<T> = with(editText) {
            text.getSpans(selectionStart, selectionEnd, clazz)
        }

        override fun <T> setSpan(typeSpan: T) {
            when (typeSpan) {
                is BackgroundColorSpan -> {
                    colorButtonBackground = typeSpan.backgroundColor.getColor
                    removeBackgroundSpan()
                }
                is ForegroundColorSpan -> {
                    colorButtonForeground = typeSpan.foregroundColor.getColor
                    removeForegroundSpan()
                }

                is ColoredUnderlineSpan -> {
                    removeUnderlinedSpan()
                    colorButtonUnderlined = Green
                }
            }

            editText.text.apply {
                setSpan(typeSpan, editText.selectionStart, editText.selectionEnd, flagSpan)
                updateSpan
            }
        }

        private val Spanned.updateSpan
            get() = noteData.noteModelFullScreen.value.also { noteModel ->
                noteModel.text = toHtml(this)
                noteModel.isChanged = true
            }

        override fun showColorPickerBackground() {
            colorPickerBackgroundIsShow = true
        }

        override fun showColorPickerForeground() {
            colorPickerForegroundIsShow = true
        }

        override fun showColorPickerUnderlined() {
            colorPickerUnderlinedIsShow = true
        }

        override fun hideColorPickerBackground() {
            colorPickerBackgroundIsShow = false
        }

        override fun hideColorPickerForeground() {
            colorPickerForegroundIsShow = false
        }

        override fun hideColorPickerUnderlined() {
            colorPickerUnderlinedIsShow = false
        }

        override fun toHtml(text: Spanned) = Html.toHtml(text, flagHtml).toString()
        override fun fromHtml(text: String): Spanned? = Html.fromHtml(text, flagHtml)
        private fun EditText.initEditText() {
            onClickListener
            with(CallbackEditText()) {
                customSelectionActionModeCallback = this
                // customInsertionActionModeCallback = this
                accessibilityDelegate = accessibilityDelegateInstance
            }
        }

        private val flagHtml by lazy { FROM_HTML_MODE_LEGACY }

        private val flagSpan by lazy { SPAN_EXCLUSIVE_EXCLUSIVE }

        private val IBinder.hideKeyboard
            get() = inputMethodManager.hideSoftInputFromWindow(this, 0)

        private val Int.getColor get() = Color(this)
    }
}