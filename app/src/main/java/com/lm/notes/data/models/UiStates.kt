package com.lm.notes.data.models

import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import com.lm.notes.ui.cells.view.ColoredUnderlineSpan
import com.lm.notes.ui.cells.view.SpanType
import com.lm.notes.ui.cells.view.SpansProvider

@Immutable
data class UiStates(
    private var isFormatMode: MutableState<Boolean> = mutableStateOf(false),
    private var colorPickerBackgroundIsShow: MutableState<Boolean> = mutableStateOf(false),
    private var colorPickerForegroundIsShow: MutableState<Boolean> = mutableStateOf(false),
    private var colorPickerUnderlinedIsShow: MutableState<Boolean> = mutableStateOf(false),
    private var colorButtonBackground: MutableState<Color> = mutableStateOf(Black),
    private var colorButtonForeground: MutableState<Color> = mutableStateOf(Black),
    private var colorButtonUnderlined: MutableState<Color> = mutableStateOf(Black),
    private var colorButtonBold: MutableState<Color> = mutableStateOf(Black),
    private var colorButtonItalic: MutableState<Color> = mutableStateOf(Black),
    private var colorButtonStrikeThrough: MutableState<Color> = mutableStateOf(Black),
    private var isSelected: MutableState<Boolean> = mutableStateOf(false),
    private var clipboardIsEmpty: MutableState<Boolean> = mutableStateOf(false),
    private var isDeleteMode: MutableState<Boolean> = mutableStateOf(false),
    private var isFullscreenMode: MutableState<Boolean> = mutableStateOf(false),
    private var isMainMode: MutableState<Boolean> = mutableStateOf(false),
    private var isExpandShare: MutableState<Boolean> = mutableStateOf(false),
    val listDeleteAble: SnapshotStateList<String> = mutableStateListOf(),
    var selection: Pair<Int, Int> = Pair(0, 0)
) {
    val getIsFormatMode get() = isFormatMode.value
    val getSelection get() = selection
    val getIsExpandShare get() = isExpandShare.value
    val getIsMainMode get() = isMainMode.value
    val getIsFullscreenMode get() = isFullscreenMode.value
    val getIsDeleteMode get() = isDeleteMode.value
    val getIsSelected get() = isSelected.value
    val getClipboardIsEmpty get() = clipboardIsEmpty.value
    val getColorPickerBackgroundIsShow get() = colorPickerBackgroundIsShow.value
    val getColorPickerForegroundIsShow get() = colorPickerForegroundIsShow.value
    val getColorButtonBackground get() = colorButtonBackground.value
    val getColorButtonForeground get() = colorButtonForeground.value
    val getColorButtonUnderlined get() = colorButtonUnderlined.value
    val getColorButtonBold get() = colorButtonBold.value
    val getColorButtonItalic get() = colorButtonItalic.value
    val getColorButtonStrikeThrough get() = colorButtonStrikeThrough.value
    val Boolean.setIsFormatMode get() = run { isFormatMode.value = this }
    private val Boolean.setIsDeleteMode get() = run { isDeleteMode.value = this }
    private val Boolean.setIsFullscreenMode get() = run { isFullscreenMode.value = this }
    private val Boolean.setIsMainMode get() = run { isMainMode.value = this }
    val Boolean.setIsExpandShare get() = run { isExpandShare.value = this }
    private val Boolean.setColorPickerBackgroundIsShow
        get() = run {
            colorPickerBackgroundIsShow.value = this
        }
    private val Boolean.setColorPickerForegroundIsShow
        get() = run {
            colorPickerForegroundIsShow.value = this
        }

    private val Color.setColorButtonBackground get() = run { colorButtonBackground.value = this }
    private val Color.setColorButtonForeground get() = run { colorButtonForeground.value = this }
    private val Color.setColorButtonUnderlined get() = run { colorButtonUnderlined.value = this }
    private val Color.setColorButtonBold get() = run { colorButtonBold.value = this }
    private val Color.setColorButtonItalic get() = run { colorButtonItalic.value = this }
    private val Color.setColorButtonStrikeThrough
        get() = run {
            colorButtonStrikeThrough.value = this
        }
    private val Boolean.setAllColorPickerIsShow
        get() = run {
            colorPickerBackgroundIsShow.value = this
            colorPickerForegroundIsShow.value = this
            colorPickerUnderlinedIsShow.value = this
        }

    val Boolean.setIsSelected get() = run { isSelected.value = this }

    val Pair<Int, Int>.setSelection get() = run { selection = this }

    val Boolean.setClipboardIsEmpty get() = run { clipboardIsEmpty.value = this }

    infix fun Color.setColor(spanType: SpanType) = when (spanType) {
        is SpanType.ColoredUnderlined -> setColorButtonUnderlined
        is SpanType.Background -> setColorButtonBackground
        is SpanType.Foreground -> setColorButtonForeground
        is SpanType.Underlined -> Green.setColorButtonUnderlined
        is SpanType.Bold -> Green.setColorButtonBold
        is SpanType.Italic -> Green.setColorButtonItalic
        is SpanType.StrikeThrough -> Green.setColorButtonStrikeThrough
        is SpanType.Relative -> Unit
    }

    fun setAllButtonsWhite(spanType: SpanType) = with(White) {
        when (spanType) {
            is SpanType.ColoredUnderlined -> setColorButtonUnderlined
            is SpanType.Background -> setColorButtonBackground
            is SpanType.Foreground -> setColorButtonForeground
            is SpanType.Underlined -> setColorButtonUnderlined
            is SpanType.Bold -> setColorButtonBold
            is SpanType.Italic -> setColorButtonItalic
            is SpanType.StrikeThrough -> setColorButtonStrikeThrough
            is SpanType.Relative -> Unit
        }
    }

    fun <T> SpansProvider.setAutoColor(type: SpanType, list: List<T>) {
        if (list.isNotEmpty())
            when (type) {
                is SpanType.Background ->
                    Color((list[0] as BackgroundColorSpan).backgroundColor).setColorButtonBackground
                is SpanType.Foreground ->
                    Color((list[0] as ForegroundColorSpan).foregroundColor).setColorButtonForeground
                is SpanType.Bold -> if (list.filteredByStyle(SpanType.Bold).isNotEmpty())
                    Green.setColorButtonBold
                is SpanType.Italic -> if (list.filteredByStyle(SpanType.Italic).isNotEmpty())
                    Green.setColorButtonItalic
                is SpanType.Underlined -> Green.setColorButtonUnderlined
                is SpanType.StrikeThrough -> Green.setColorButtonStrikeThrough
                is SpanType.ColoredUnderlined ->
                    Color((list[0] as ColoredUnderlineSpan).underlineColor).setColorButtonUnderlined
                is SpanType.Relative -> Unit
            }
    }

    fun setAllButtonsWhite() = with(White) {
        setColorButtonUnderlined
        setColorButtonBackground
        setColorButtonForeground
        setColorButtonUnderlined
        setColorButtonBold
        setColorButtonItalic
        setColorButtonStrikeThrough
    }

    fun SpanType.ifNoSpans() = when (this) {
        is SpanType.Background -> {
            if (!getColorPickerBackgroundIsShow) true.setColorPickerBackgroundIsShow
            else false.setColorPickerBackgroundIsShow
        }
        is SpanType.Foreground -> {
            if (!getColorPickerForegroundIsShow) true.setColorPickerForegroundIsShow
            else false.setColorPickerForegroundIsShow
        }
        else -> Unit
    }

    fun hideFormatPanel() = with(false) {
        setIsFormatMode
        setColorPickerBackgroundIsShow
        setColorPickerForegroundIsShow
    }

    fun onClickEditText() {
        if (getIsFormatMode) hideFormatPanel()
        false.setAllColorPickerIsShow
        false.setIsSelected
    }

    fun setDeleteMode(){
        true.setIsDeleteMode
        false.setIsMainMode
    }

    fun setMainMode(){
        false.setIsFullscreenMode
        true.setIsMainMode
        false.setIsExpandShare
        false.setIsDeleteMode
        listDeleteAble.clear()
    }

    fun setFullScreenMode(){
        true.setIsFullscreenMode
        false.setIsMainMode
    }

    fun addToDeleteAbleList(id: String){
        listDeleteAble.add(id)
    }

    fun removeFromDeleteAbleList(id: String){
        listDeleteAble.remove(id)
    }
}