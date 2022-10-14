package com.lm.notes.ui.cells.view

import android.annotation.SuppressLint
import android.text.Html
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatClear
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.text.HtmlCompat
import androidx.core.text.set
import androidx.core.text.toHtml
import com.lm.notes.data.local_data.NoteData
import com.lm.notes.data.models.UiStates
import com.lm.notes.utils.getAction
import com.lm.notes.utils.log
import javax.inject.Inject


interface EditTextController {

    fun setText(text: String)

    fun SpanType.setSpan()

    fun setButtonColors()

    fun SpanType.removeSpan()

    fun removeAllSpans()

    fun <T> listSpans(clazz: Class<T>): List<T>

    fun SpanType.isHaveSpans(): Boolean

    fun <T> List<T>.filteredByStyle(spanType: SpanType): List<T>

    val editText: EditText

    fun fromHtml(text: String): Spanned

    fun setFormatMode()

    fun updateText()

    fun setRelativeSpan(scale: Float)

    fun setSelection()

    fun saveSelection()

    fun setEditMode()

    fun removeSelection()

    fun hideKeyboard()

    fun ImageVector.buttonFormatAction()

    fun SpanType.getType(color: Int)

    class Base @Inject constructor(
        private val noteData: NoteData,
        override val editText: EditText,
        private val uiStates: UiStates,
        private val inputMethodManager: InputMethodManager
    ) : EditTextController {

        init {
            initEditText()
        }

        @SuppressLint("ClickableViewAccessibility")
        private fun initEditText() {
            AccessibilityDelegate(this@Base, uiStates).also { listener ->
                with(editText) {
                    with(uiStates) {
                        setOnClickListener {
                            if (getIsFormatMode) {
                                setEditMode(); onClickEditText(); removeSelection(); editText.clearFocus()
                            } else {
                                inputMethodManager.showSoftInput(editText, 0)
                            }
                        }
                    }
                    CallbackEditText(uiStates, this@Base, editText).also { callback ->
                        accessibilityDelegate = listener
                        customSelectionActionModeCallback = callback
                        customInsertionActionModeCallback = callback
                        // movementMethod = LinkMovementMethod.getInstance()
                    }
                }
            }
        }

        override fun setText(text: String) = editText.setText(Html.fromHtml(text, htmlMode).trim())

        override fun SpanType.removeSpan() {
            uiStates.setButtonWhite(this)
            listSpans(instance.javaClass).filteredByStyle(this).forEach {
                with(editText.text) {
                    editText.setSpansAroundSelected(getSpanStart(it), getSpanEnd(it))
                    { instance }; removeSpan(it)
                }
            }
            updateText()
        }

        override fun SpanType.getType(color: Int) =
            if (this is SpanType.Background) SpanType.Background(color).setSpan()
            else SpanType.Foreground(color).setSpan()

        override fun removeAllSpans() {
            listClasses.forEach {
                listSpans(it.instance.javaClass).forEach { span ->
                    editText.text.removeSpan(span); uiStates.setAllButtonsWhite()
                }
            }
        }

        override fun SpanType.setSpan() {
            removeSpan(); uiStates.apply { Color(getColor) setColor this@setSpan }
            set().apply { updateText() }
        }

        override fun setButtonColors() {
            uiStates.setAllButtonsWhite()
            with(uiStates) { listClasses.forEach { setAutoColor(it, listSpans(it.clazz)) } }
        }

        private val listClasses by lazy {
            listOf(
                SpanType.StrikeThrough, SpanType.Underlined, SpanType.Bold, SpanType.Italic,
                SpanType.Background(), SpanType.Foreground(), SpanType.Url
            )
        }

        private fun <T : Any> EditText.setSpansAroundSelected(start: Int, end: Int, span: () -> T) {
            if (start < selectionStart) text[start..selectionStart] = span.invoke()
            if (end > selectionEnd) text[selectionEnd..end] = span.invoke()
        }

        private fun SpanType.set() = with(editText) {
            text.setSpan(instance, selectionStart, selectionEnd, flagSpan)
        }

        override fun updateText() = with(noteData.noteModelFullScreen.value) {
            text = editText.text.toHtml(); isChanged = true
        }

        override fun setRelativeSpan(scale: Float) = with(editText) {
            text.setSpan(
                RelativeSizeSpan(scale), 0, text.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE
            ); updateText()
        }

        override fun <T> listSpans(clazz: Class<T>): List<T> = with(editText) {
            text.getSpans(selectionStart, selectionEnd, clazz).asList()
        }

        override fun SpanType.isHaveSpans() =
            listSpans(clazz).filteredByStyle(this).isNotEmpty()

        override fun <T> List<T>.filteredByStyle(spanType: SpanType) =
            if (isNotEmpty()) get(0).getList(this, spanType) else emptyList()

        private fun <T> T.getList(list: List<T>, type: SpanType) =
            when (this) {
                is StyleSpan -> list.filter { (it as StyleSpan).style == type.getTypeFace(type) }
                else -> list
            }

        override fun setFormatMode() = with(editText) {
            hideKeyboard()
            showSoftInputOnFocus = false
            isCursorVisible = false
            with(uiStates) {
                true.setIsFormatMode
                true.setIsSelected
            }
        }

        override fun setEditMode() = with(editText) {
            if (!showSoftInputOnFocus) showSoftInputOnFocus = true
            if (!isCursorVisible) isCursorVisible = true; with(uiStates) {
            false.setIsSelected
        }
            removeSelection()
        }

        override fun removeSelection() = with(editText) {
            with(uiStates) {
                if (getSelection != Pair(-1, -1)) {
                    setSelection(editText.text.length, editText.text.length)
                    Pair(-1, -1).setSelection
                }
            }
        }

        override fun fromHtml(text: String): Spanned = HtmlCompat.fromHtml(text, flagHtml)

        private val flagSpan by lazy { Spanned.SPAN_EXCLUSIVE_EXCLUSIVE }

        private val flagHtml by lazy { Html.FROM_HTML_MODE_LEGACY }

        override fun hideKeyboard()
        { inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0) }

        override fun setSelection() = with(uiStates.getSelection) {
            if (this != Pair(0, 0) && this != Pair(-1, -1)) {
                with(editText) { requestFocus(); setSelection(first, second) }
            }
        }

        override fun saveSelection() {
            with(editText) {
                with(uiStates) {
                    Pair(selectionStart, selectionEnd).setSelection; true.setIsSelected
                }
            }
        }

        override fun ImageVector.buttonFormatAction() =
            with(uiStates) {
                with(getSpanType()) {
                    if (isHaveSpans()) removeSpan() else getAction(uiStates, this)
                }
                if (this@buttonFormatAction == Icons.Rounded.FormatClear) removeAllSpans()
            }

        private val htmlMode by lazy { Html.FROM_HTML_MODE_LEGACY }
    }
}