package com.lm.notes.ui.cells.view

import android.os.IBinder
import android.text.Html
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatColorFill
import androidx.compose.material.icons.rounded.FormatColorText
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.text.set
import com.lm.notes.data.local_data.NoteData
import com.lm.notes.data.models.UiStates
import com.lm.notes.utils.getAction
import com.lm.notes.utils.getSpanType
import javax.inject.Inject

interface SpansProvider {

    fun SpanType.setSpan()

    fun SpanType.removeSpan()

    fun removeAllSpans()

    fun <T> listSpans(clazz: Class<T>): List<T>

    fun SpanType.isHaveSpans(): Boolean

    fun <T> List<T>.filteredByStyle(spanType: SpanType): List<T>

    val editText: EditText

    fun toHtml(text: Spanned): String

    fun fromHtml(text: String): Spanned?

    fun setFormatMode()

    class Base @Inject constructor(
        private val noteData: NoteData,
        override val editText: EditText,
        private val uiStates: UiStates,
        private val inputMethodManager: InputMethodManager
    ) : SpansProvider {

        override fun SpanType.removeSpan() {
            uiStates.setBlack(this)
            listSpans(instance.javaClass).filteredByStyle(this).forEach {
                with(editText.text) {
                    editText.setSpansAroundSelected(getSpanStart(it), getSpanEnd(it))
                    { instance }
                    removeSpan(it)
                }
            }
            updateText
        }

        override fun removeAllSpans() = editText.text.clearSpans()

        override fun SpanType.setSpan() {
            removeSpan()
            uiStates.apply { Color(getColor).setColor(this@setSpan) }
            set().apply { updateText }
        }

        private fun <T : Any> EditText.setSpansAroundSelected(start: Int, end: Int, span: () -> T) {
            if (start < selectionStart) text[start..selectionStart] = span.invoke()
            if (end > selectionEnd) text[selectionEnd..end] = span.invoke()
        }

        private fun SpanType.set() = with(editText) {
            text.setSpan(instance, selectionStart, selectionEnd, flagSpan)
        }

        private val updateText
            get() = noteData.noteModelFullScreen.value.apply {
                text = toHtml(editText.text); isChanged = true
            }

        override fun <T> listSpans(clazz: Class<T>): List<T> = with(editText) {
            text.getSpans(selectionStart, selectionEnd, clazz).asList()
        }

        override fun SpanType.isHaveSpans() =
            listSpans(clazz).filteredByStyle(this).isNotEmpty()

        override fun <T> List<T>.filteredByStyle(spanType: SpanType) =
            if (isNotEmpty()) get(0).getList(this, spanType)
            else emptyList()

        private fun <T> T.getList(list: List<T>, type: SpanType) =
            when (this) {
                is StyleSpan -> list.filter { (it as StyleSpan).style == type.getTypeFace(type) }
                else -> list
            }

        override fun setFormatMode() =
            with(editText) { windowToken?.hideKeyboard; showSoftInputOnFocus = false }

        override fun toHtml(text: Spanned) = Html.toHtml(text, flagHtml).toString()

        override fun fromHtml(text: String): Spanned? = Html.fromHtml(text, flagHtml)

        private val flagSpan by lazy { Spanned.SPAN_EXCLUSIVE_EXCLUSIVE }

        private val flagHtml by lazy { Html.FROM_HTML_MODE_LEGACY }

        private val IBinder.hideKeyboard
            get() = inputMethodManager.hideSoftInputFromWindow(this, 0)
    }
}