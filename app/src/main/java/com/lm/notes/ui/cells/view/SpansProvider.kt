package com.lm.notes.ui.cells.view

import android.os.IBinder
import android.text.Html
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.compose.ui.graphics.Color
import androidx.core.text.set
import com.lm.notes.data.local_data.NoteData
import com.lm.notes.data.models.UiStates
import com.lm.notes.utils.log
import javax.inject.Inject

interface SpansProvider {

    fun SpanType.setSpan()

    fun setButtonColors()

    fun clearFocus()

    fun SpanType.removeSpan()

    fun isSelected(): Boolean

    fun removeAllSpans()

    fun <T> listSpans(clazz: Class<T>): List<T>

    fun SpanType.isHaveSpans(): Boolean

    fun <T> List<T>.filteredByStyle(spanType: SpanType): List<T>

    val editText: EditText

    fun toHtml(text: Spanned): String

    fun fromHtml(text: String): Spanned?

    fun setFormatMode()

    fun updateText(scale: Float)

    fun setRelativeSpan(scale: Float)

    fun setSelection()

    fun saveSelection()

    class Base @Inject constructor(
        private val noteData: NoteData,
        override val editText: EditText,
        private val uiStates: UiStates,
        private val inputMethodManager: InputMethodManager
    ) : SpansProvider {

        override fun SpanType.removeSpan() {
            uiStates.setAllButtonsWhite(this)
            listSpans(instance.javaClass).filteredByStyle(this).forEach {
                with(editText.text) {
                    editText.setSpansAroundSelected(getSpanStart(it), getSpanEnd(it))
                    { instance }
                    uiStates.log
                    removeSpan(it)
                }
            }
            updateText(-1f)
        }

        override fun isSelected() = (editText.selectionEnd - editText.selectionStart) > 0

        override fun removeAllSpans() {
            listClasses.forEach {
                listSpans(it.instance.javaClass).forEach { span ->
                    editText.text.removeSpan(span)
                    uiStates.setAllButtonsWhite()
                }
            }
        }

        override fun SpanType.setSpan() {
            removeSpan()
            uiStates.apply { Color(getColor) setColor this@setSpan }
            set().apply { updateText(-1f) }
        }

        override fun setButtonColors() {
            with(uiStates) {
                listClasses.forEach { setAutoColor(it, listSpans(it.clazz)) }
            }
        }

        private val listClasses by lazy {
            listOf(
                SpanType.StrikeThrough, SpanType.Underlined, SpanType.Bold, SpanType.Italic,
                SpanType.Background(), SpanType.Foreground(), SpanType.ColoredUnderlined()
            )
        }

        override fun clearFocus() {
            editText.clearFocus()
        }

        private fun <T : Any> EditText.setSpansAroundSelected(start: Int, end: Int, span: () -> T) {
            if (start < selectionStart) text[start..selectionStart] = span.invoke()
            if (end > selectionEnd) text[selectionEnd..end] = span.invoke()
        }

        private fun SpanType.set() = with(editText) {
            text.setSpan(instance, selectionStart, selectionEnd, flagSpan)
        }

        override fun updateText(scale: Float) = with(noteData.noteModelFullScreen.value) {
            text = toHtml(editText.text); isChanged = true; if (scale != -1f) textScaleState =
            scale
        }

        override fun setRelativeSpan(scale: Float) = with(editText) {
            text.setSpan(RelativeSizeSpan(scale), 0, text.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            updateText(scale)
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

        override fun setFormatMode() = with(editText) {
            windowToken?.hideKeyboard; showSoftInputOnFocus = false
            isCursorVisible = false
        }

        override fun toHtml(text: Spanned) = Html.toHtml(text, flagHtml).toString()

        override fun fromHtml(text: String): Spanned? = Html.fromHtml(text, flagSpan)

        private val flagSpan by lazy { Spanned.SPAN_EXCLUSIVE_EXCLUSIVE }

        private val flagHtml by lazy { Html.FROM_HTML_MODE_LEGACY }

        private val IBinder.hideKeyboard
            get() = inputMethodManager.hideSoftInputFromWindow(this, 0)

        override fun setSelection() = with(uiStates.getSelection) {
            if (this != Pair(0, 0)) {
                editText.requestFocus()
                editText.setSelection(first, second)
            }
        }

        override fun saveSelection() {
            with(editText) {
                with(uiStates) {
                    Pair(selectionStart, selectionEnd).setSelection
                    true.setIsSelected
                }
            }
        }
    }
}