package com.lm.notes.data.models

import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import com.lm.notes.ui.cells.view.ColoredUnderlineSpan
import com.lm.notes.ui.cells.view.SpanType
import com.lm.notes.ui.cells.view.SpansProvider

data class UiStates(
    private var longClickState: MutableState<Boolean> = mutableStateOf(false),
    private var colorPickerBackgroundIsShow: MutableState<Boolean> = mutableStateOf(false),
    private var colorPickerForegroundIsShow: MutableState<Boolean> = mutableStateOf(false),
    private var colorPickerUnderlinedIsShow: MutableState<Boolean> = mutableStateOf(false),
    private var colorButtonBackground: MutableState<Color> = mutableStateOf(Color.Black),
    private var colorButtonForeground: MutableState<Color> = mutableStateOf(Color.Black),
    private var colorButtonUnderlined: MutableState<Color> = mutableStateOf(Color.Black),
    private var colorButtonBold: MutableState<Color> = mutableStateOf(Color.Black),
    private var colorButtonItalic: MutableState<Color> = mutableStateOf(Color.Black),
    private var colorButtonStrikeThrough: MutableState<Color> = mutableStateOf(Color.Black),
) {
    val getLongClickState get() = longClickState.value
    val getColorPickerBackgroundIsShow get() = colorPickerBackgroundIsShow.value
    val getColorPickerForegroundIsShow get() = colorPickerForegroundIsShow.value
    val getColorPickerUnderlinedIsShow get() = colorPickerUnderlinedIsShow.value
    val getColorButtonBackground get() = colorButtonBackground.value
    val getColorButtonForeground get() = colorButtonForeground.value
    val getColorButtonUnderlined get() = colorButtonUnderlined.value
    val getColorButtonBold get() = colorButtonBold.value
    val getColorButtonItalic get() = colorButtonItalic.value
    val getColorButtonStrikeThrough get() = colorButtonStrikeThrough.value
    val Boolean.setLongClickState get() = run { longClickState.value = this }
    private val Boolean.setColorPickerBackgroundIsShow
        get() = run {
            colorPickerBackgroundIsShow.value = this
        }
    private val Boolean.setColorPickerForegroundIsShow
        get() = run {
            colorPickerForegroundIsShow.value = this
        }
    val Boolean.setColorPickerUnderlinedIsShow
        get() = run {
            colorPickerUnderlinedIsShow.value = this
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

    fun Color.setColor(spanType: SpanType) = when (spanType) {
        is SpanType.ColoredUnderlined -> setColorButtonUnderlined
        is SpanType.Background -> setColorButtonBackground
        is SpanType.Foreground -> setColorButtonForeground
        is SpanType.Underlined -> Green.setColorButtonUnderlined
        is SpanType.Bold -> Green.setColorButtonBold
        is SpanType.Italic -> Green.setColorButtonItalic
        is SpanType.StrikeThrough -> Green.setColorButtonStrikeThrough
    }

    fun setBlack(spanType: SpanType) = with(Black) {
        when (spanType) {
            is SpanType.ColoredUnderlined -> setColorButtonUnderlined
            is SpanType.Background -> setColorButtonBackground
            is SpanType.Foreground -> setColorButtonForeground
            is SpanType.Underlined -> setColorButtonUnderlined
            is SpanType.Bold -> setColorButtonBold
            is SpanType.Italic -> setColorButtonItalic
            is SpanType.StrikeThrough -> setColorButtonStrikeThrough
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
            }
    }

        fun setBlack() = with(Black) {
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

        fun hideFormatPanel() {
            false.setLongClickState
            false.setColorPickerBackgroundIsShow
            false.setColorPickerForegroundIsShow
        }

        fun onClickEditText() {
            if (getLongClickState) hideFormatPanel()
            false.setAllColorPickerIsShow
        }
    }