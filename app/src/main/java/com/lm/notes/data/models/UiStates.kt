package com.lm.notes.data.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.toArgb
import com.lm.notes.ui.cells.view.SpanType

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
    val Boolean.setColorPickerBackgroundIsShow
        get() = run {
            colorPickerBackgroundIsShow.value = this
        }
    val Boolean.setColorPickerForegroundIsShow
        get() = run {
            colorPickerForegroundIsShow.value = this
        }
    val Boolean.setColorPickerUnderlinedIsShow
        get() = run {
            colorPickerUnderlinedIsShow.value = this
        }
    val Color.setColorButtonBackground get() = run { colorButtonBackground.value = this }
    val Color.setColorButtonForeground get() = run { colorButtonForeground.value = this }
    val Color.setColorButtonUnderlined get() = run { colorButtonUnderlined.value = this }
    val Color.setColorButtonBold get() = run { colorButtonBold.value = this }
    val Color.setColorButtonItalic get() = run { colorButtonItalic.value = this }
    val Color.setColorButtonStrikeThrough get() = run { colorButtonStrikeThrough.value = this }
    val Boolean.setAllColorPickerIsShow
        get() = run {
            colorPickerBackgroundIsShow.value = this
            colorPickerForegroundIsShow.value = this
            colorPickerUnderlinedIsShow.value = this
        }

    val Color.setAllButtonsColor
        get() = run {
            colorButtonUnderlined.value = this
            colorButtonForeground.value = this
            colorButtonBackground.value = this
            colorButtonItalic.value = this
            colorButtonBold.value = this
            colorButtonStrikeThrough.value = this
        }

    fun setColor(color: Color, spanType: SpanType) = when (spanType) {
        is SpanType.ColoredUnderlined -> color.setColorButtonUnderlined
        is SpanType.Background -> color.setColorButtonBackground
        is SpanType.Foreground -> color.setColorButtonForeground
        is SpanType.Underlined -> Green.setColorButtonUnderlined
        is SpanType.Bold -> Green.setColorButtonBold
        is SpanType.Italic -> Green.setColorButtonItalic
        is SpanType.StrikeThrough -> Green.setColorButtonStrikeThrough
    }
}