package com.lm.notes.ui.cells.view

import android.text.Html
import android.text.Spanned
import android.text.style.StyleSpan
import android.widget.EditText
import androidx.compose.ui.graphics.Color
import androidx.core.text.set
import com.lm.notes.data.local_data.NoteData
import com.lm.notes.data.models.UiStates
import com.lm.notes.utils.log
import javax.inject.Inject

interface SpansProvider {

    fun SpanType.setSpan()

    fun EditText.removeSpan(spanType: SpanType)

    fun removeAllSpans()

    fun <T> listSpans(clazz: Class<T>): Array<T>

    fun <T> isHaveSpans(clazz: Class<T>, type: SpanType): Boolean

    fun <T> filteredByStyle(spanType: SpanType, spansArray: Array<T>): List<T>

    val editText: EditText

    val uiStates: UiStates

    fun toHtml(text: Spanned): String

    fun fromHtml(text: String): Spanned?

    class Base @Inject constructor(
        private val noteData: NoteData,
        override val editText: EditText,
        override val uiStates: UiStates
    ) : SpansProvider {

        override fun EditText.removeSpan(spanType: SpanType) {
                filteredByStyle(spanType, listSpans(spanType.instance.javaClass)).forEach {
                        setSpansAroundSelected(text.getSpanStart(it), text.getSpanEnd(it))
                        { spanType.instance }; text.removeSpan(it)
                    }
            updateText
        }

        override fun removeAllSpans() = editText.text.clearSpans()

        override fun SpanType.setSpan() {
            editText.removeSpan(this); uiStates.setColor(Color(getColor), this)
            set().apply { updateText }
        }

        private fun <T : Any> EditText.setSpansAroundSelected(start: Int, end: Int, span: () -> T) {
            if (start < selectionStart) text[start..selectionStart] = span.invoke()
            if (end > selectionEnd) text[selectionEnd..end] = span.invoke()
        }

        private fun SpanType.set() = with(editText) {
            text.setSpan(instance.apply { log }, selectionStart, selectionEnd, flagSpan)
        }

        private val updateText get() = noteData.noteModelFullScreen.value.apply {
                text = toHtml(editText.text) ;isChanged = true
            }

        override fun <T> listSpans(clazz: Class<T>): Array<T> = with(editText) {
            text.getSpans(selectionStart, selectionEnd, clazz)
        }

        override fun <T> isHaveSpans(clazz: Class<T>, type: SpanType) =
            filteredByStyle(type, listSpans(clazz)).isNotEmpty()

        override fun <T> filteredByStyle(spanType: SpanType, spansArray: Array<T>) =
            spansArray.getStyle(spanType)

        private fun <T> Array<T>.getStyle(spanType: SpanType) = if (isNotEmpty()) {
                if (get(0) is StyleSpan) byTypeface(spanType, get(0) as StyleSpan) else asList()
            } else emptyList()

        private fun <T> Array<T>.byTypeface(spanType: SpanType, style: StyleSpan) =
            filter { style.style == spanType.getTypeFace }

        override fun toHtml(text: Spanned) = Html.toHtml(text, flagHtml).toString()

        override fun fromHtml(text: String): Spanned? = Html.fromHtml(text, flagHtml)

        private val flagSpan by lazy { Spanned.SPAN_EXCLUSIVE_EXCLUSIVE }

        private val flagHtml by lazy { Html.FROM_HTML_MODE_LEGACY }
    }
}