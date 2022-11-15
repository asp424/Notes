package com.lm.notes.ui.cells.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color.YELLOW
import android.net.Uri
import android.text.Html
import android.text.Spanned
import android.text.style.*
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.core.text.set
import androidx.core.text.toHtml
import com.lm.notes.data.local_data.NoteData
import com.lm.notes.data.models.UiStates
import com.lm.notes.ui.core.SpanType
import com.lm.notes.ui.core.SpanType.Bold.listClasses
import com.lm.notes.utils.getAction
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import javax.inject.Inject

interface EditTextController {

    fun setNewText(newText: String)

    fun SpanType.setSpan()

    fun setButtonColors()

    fun SpanType.removeSpan()

    fun removeAllSpans()

    fun <T> listSpansInSelection(clazz: Class<T>): List<T>

    fun <T> listSpansInAllText(clazz: Class<T>): List<T>

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

    fun showKeyboard()

    fun SpanType.buttonFormatAction()

    fun SpanType.getType(color: Int)

    fun showDialogChangeKeyboard()

    fun removeUnderLinedFromKeyBoard()

    fun touchOffsetOnActionUp(
        view: TextView, event: MotionEvent, onActionUp: (Int) -> Unit
    ): Boolean

    fun listOfUrlSpans(onEachSpan: Pair<Int, Int>.(Uri) -> Unit, onEmpty: () -> Unit)

    fun Pair<Int, Int>.checkForClick(offset: Int, onCheck: () -> Unit)

    fun Pair<Int, Int>.setHighLightAndOpenLink(view: TextView, uri: Uri)

    fun EditText.onClickAction()

    fun onDestroyContextMenu()

    fun findEnglish()

    fun setLinesCount()

    fun createEditText(): EditText

    class Base @Inject constructor(
        private val noteData: NoteData,
        private val editTextBuilder: Function0<@JvmSuppressWildcards EditText>,
        private val uiStates: UiStates,
        private val inputMethodManager: InputMethodManager,
        private val coroutineDispatcher: CoroutineDispatcher,
        private val callbackEditText: CallbackEditText
    ) : EditTextController {

        private var _editText: EditText? = null
        override val editText get() = _editText?: editTextBuilder()

        override fun createEditText() = editTextBuilder().apply {
            _editText = this
            _editText?.initEditText()
        }

        @SuppressLint("ClickableViewAccessibility")
        private fun EditText.initEditText() {
            setOnTouchListener { view, event ->
                touchOffsetOnActionUp(view as TextView, event) { offset ->
                    listOfUrlSpans(
                        onEachSpan = { uri ->
                            checkForClick(offset) { setHighLightAndOpenLink(view, uri) }
                        }, onEmpty = { showKeyboard() }
                    )
                }
            }
            accessibilityDelegate = AccessibilityDelegate(this@Base, uiStates)
                customSelectionActionModeCallback = callbackEditText
                customInsertionActionModeCallback = callbackEditText
            setOnClickListener { onClickAction() }
        }

        override fun findEnglish() {
            with(uiStates) {
                with(editText) {
                    if (text.contains(Regex("[А-я , ; ё]"))) {
                        true.setTranslateEnable
                        setText(text.replace(Regex("[А-я , ; ё]"), ""))
                        setLinesCount()
                    } else {
                        setNewText(noteData.noteModelFullScreen.value.text)
                        false.setTranslateEnable
                    }
                }
            }
        }

        override fun setNewText(newText: String) = with(editText) {
            CoroutineScope(Main).launch {
                setText(Html.fromHtml(newText, htmlMode).trim())
                setLinesCount()
            }
            Unit
        }

        override fun setLinesCount() {
            with(editText) {
                post {
                    with(uiStates) { lineCount.setLinesCounter }
                }
            }
        }

        override fun SpanType.removeSpan() {
            uiStates.setButtonWhite(this)
            listSpansInSelection(instance.javaClass).filteredByStyle(this).forEach {
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
                listSpansInSelection(it.instance.javaClass).forEach { span ->
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
            with(uiStates) {
                listClasses.forEach { setAutoColor(it, listSpansInSelection(it.clazz)) }
            }
        }

        private fun <T : Any> EditText.setSpansAroundSelected(
            start: Int, end: Int, span: () -> T
        ) {
            if (start < selectionStart) text[start..selectionStart] = span.invoke()
            if (end > selectionEnd) text[selectionEnd..end] = span.invoke()
        }

        private fun SpanType.set() = with(editText) {
            text.setSpan(instance, selectionStart, selectionEnd, flagSpan)
        }

        override fun updateText() = with(noteData.noteModelFullScreen.value) {
            text = editText.text.toHtml(); isChanged = true
            with(uiStates) { LoadStatesEditText.Success.setIsSetTextInEditText }
        }

        override fun setRelativeSpan(scale: Float) = with(editText) {
            text.setSpan(
                RelativeSizeSpan(scale), 0, text.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE
            ); updateText()
        }

        override fun <T> listSpansInSelection(clazz: Class<T>): List<T> = with(editText) {
            text.getSpans(selectionStart, selectionEnd, clazz).asList()
        }

        override fun <T> listSpansInAllText(clazz: Class<T>): List<T> = with(editText) {
            text.getSpans(0, length(), clazz).asList()
        }

        override fun SpanType.isHaveSpans() =
            listSpansInSelection(clazz).filteredByStyle(this).isNotEmpty()

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
            uiStates.setFormat()
        }

        override fun showDialogChangeKeyboard() = inputMethodManager.showInputMethodPicker()

        override fun setEditMode() = with(editText) {
            if (!showSoftInputOnFocus) showSoftInputOnFocus = true
            if (!isCursorVisible) isCursorVisible = true; with(uiStates) {
            false.setIsSelected
        }
            removeSelection()
        }

        override fun removeUnderLinedFromKeyBoard() = with(editText.text) {
            for (span in this.getSpans(0, length, UnderlineSpan::class.java)) {
                removeSpan(span)
            }
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

        override fun hideKeyboard() {
            inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
        }

        override fun showKeyboard() {
            if (!uiStates.getIsFormatMode) {
                inputMethodManager.showSoftInput(editText, 0)
            }
        }

        override fun setSelection() = with(uiStates.getSelection) {
            if (this != Pair(0, 0) && this != Pair(-1, -1)) {
                with(editText) { requestFocus(); setSelection(first, second) }
            }
        }

        override fun saveSelection() {
            with(editText) {
                with(uiStates) { Pair(selectionStart, selectionEnd).setSelection; setFormat() }
            }
        }

        override fun SpanType.buttonFormatAction() =
            with(uiStates) {
                with(this@buttonFormatAction) {
                    if (isHaveSpans()) removeSpan() else getAction(uiStates, this)
                }
                if (this@buttonFormatAction == SpanType.Clear) removeAllSpans()
            }

        override fun touchOffsetOnActionUp(
            view: TextView, event: MotionEvent, onActionUp: (Int) -> Unit
        ) = with(event) {
            if (action == ACTION_UP) with(view.layout) {
                val line = getLineForVertical(y.toInt() - view.paddingTop)
                onActionUp(getOffsetForHorizontal(line, x - view.paddingStart))
            }
            false
        }

        override fun listOfUrlSpans(
            onEachSpan: Pair<Int, Int>.(Uri) -> Unit, onEmpty: () -> Unit
        ) = with(listSpansInAllText(URLSpan::class.java)) {
            if (isNotEmpty()) forEach {
                onEachSpan(
                    with(editText.text) { Pair(getSpanStart(it), getSpanEnd(it)) },
                    it.url.toUri()
                )
            } else onEmpty()
        }

        override fun Pair<Int, Int>.checkForClick(offset: Int, onCheck: () -> Unit) =
            if (offset in (first..second) &&
                !uiStates.getIsFormatMode && offset in editText.text.indices
            ) {
                onCheck(); editText.isEnabled = false
            } else Unit

        override fun Pair<Int, Int>.setHighLightAndOpenLink(view: TextView, uri: Uri) {
            BackgroundColorSpan(YELLOW).also { span ->
                setHighLight.invoke(span)
                view.context.openLink.invoke(uri)
                removeHighLight.invoke(span)
            }
        }

        override fun EditText.onClickAction() {
            if (uiStates.getIsFormatMode) {
                setEditMode(); uiStates.onClickEditText(); removeSelection()
                clearFocus()
            }
        }

        private val Context.openLink: (Uri) -> Unit
            get() =
                { startActivity(urlLinkIntent.invoke(it)) }

        private val removeHighLight: (BackgroundColorSpan) -> Unit
            get() = {
                CoroutineScope(coroutineDispatcher).launch {
                    delay(500); editText.text.removeSpan(it)
                }
            }

        private val Pair<Int, Int>.setHighLight: (BackgroundColorSpan) -> Unit
            get() = { editText.text.setSpan(it, first, second, flagSpan) }

        private val urlLinkIntent: (Uri) -> Intent
            get() = {
                Intent(Intent.ACTION_VIEW).apply {
                    data = it
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            }

        override fun onDestroyContextMenu() {
            CoroutineScope(Dispatchers.Main).launch {
                with(uiStates) {
                    editText.clearFocus()
                    delay(200)
                    if (getIsFormatMode && getSetSelectionEnable) {
                        setEditMode(); onClickEditText()
                    }
                }
            }
        }

        private val htmlMode by lazy { Html.FROM_HTML_MODE_LEGACY }
    }
}
