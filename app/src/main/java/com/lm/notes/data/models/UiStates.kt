package com.lm.notes.data.models

import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.widget.EditText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import com.lm.notes.ui.cells.view.SpanType
import com.lm.notes.ui.cells.view.EditTextController
import com.lm.notes.ui.theme.main
import com.lm.notes.utils.animScale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Immutable
@Stable
data class UiStates(
    private var isFormatMode: MutableState<Boolean> = mutableStateOf(false),
    private var setSelectionEnable: MutableState<Boolean> = mutableStateOf(false),
    private var colorPickerBackgroundIsShow: MutableState<Boolean> = mutableStateOf(false),
    private var colorPickerForegroundIsShow: MutableState<Boolean> = mutableStateOf(false),
    private var colorPickerUnderlinedIsShow: MutableState<Boolean> = mutableStateOf(false),
    private var colorButtonBackground: MutableState<Color> = mutableStateOf(Black),
    private var colorButtonForeground: MutableState<Color> = mutableStateOf(Black),
    private var colorButtonUnderlined: MutableState<Color> = mutableStateOf(Black),
    private var colorButtonBold: MutableState<Color> = mutableStateOf(Black),
    private var colorButtonItalic: MutableState<Color> = mutableStateOf(Black),
    private var colorButtonClick: MutableState<Color> = mutableStateOf(Black),
    private var colorButtonStrikeThrough: MutableState<Color> = mutableStateOf(Black),
    private var isSelected: MutableState<Boolean> = mutableStateOf(false),
    private var clipboardIsEmpty: MutableState<Boolean> = mutableStateOf(false),
    private var isDeleteMode: MutableState<Boolean> = mutableStateOf(false),
    private var isFullscreenMode: MutableState<Boolean> = mutableStateOf(false),
    private var isMainMode: MutableState<Boolean> = mutableStateOf(true),
    private var isExpandShare: MutableState<Boolean> = mutableStateOf(false),
    private var settingsVisible: MutableState<Boolean> = mutableStateOf(false),
    private var notShareVisible: MutableState<Boolean> = mutableStateOf(true),
    private var textIsEmpty: MutableState<Boolean> = mutableStateOf(true),
    val listDeleteAble: SnapshotStateList<String> = mutableStateListOf(),
    val mainColor: MutableState<Color> = mutableStateOf(main),
    val secondColor: MutableState<Color> = mutableStateOf(main),
    val isClickableNote: MutableState<Boolean> = mutableStateOf(true),
    var selection: Pair<Int, Int> = Pair(-1, -1)
) {
    val getIsFormatMode get() = isFormatMode.value
    val getSetSelectionEnable get() = setSelectionEnable.value
    val getIsClickableNote get() = isClickableNote.value
    val getTextIsEmpty get() = textIsEmpty.value
    val getNotShareVisible get() = notShareVisible.value
    val getMainColor get() = mainColor.value
    val getSecondColor get() = secondColor.value
    val getSettingsVisible get() = settingsVisible.value
    val getSelection get() = selection
    val getIsExpandShare get() = isExpandShare.value
    val getIsMainMode get() = isMainMode.value
    val getIsFullscreenMode get() = isFullscreenMode.value
    val getIsDeleteMode get() = isDeleteMode.value
    private val getIsSelected get() = isSelected.value
    private val getClipboardIsEmpty get() = clipboardIsEmpty.value
    private val getColorPickerBackgroundIsShow get() = colorPickerBackgroundIsShow.value
    private val getColorPickerForegroundIsShow get() = colorPickerForegroundIsShow.value
    private val getColorButtonBackground get() = colorButtonBackground.value
    private val getColorButtonForeground get() = colorButtonForeground.value
    private val getColorButtonUnderlined get() = colorButtonUnderlined.value
    private val getColorButtonBold get() = colorButtonBold.value
    private val getColorButtonClick get() = colorButtonClick.value
    private val getColorButtonItalic get() = colorButtonItalic.value
    private val getColorButtonStrikeThrough get() = colorButtonStrikeThrough.value
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
    private val Color.setColorButtonClick get() = run { colorButtonClick.value = this }
    val Color.setMainColor get() = run { mainColor.value = this }
    val Color.setSecondColor get() = run { secondColor.value = this }
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

    val Boolean.setSetSelectionEnable get() = run { setSelectionEnable.value = this }

    val Boolean.setIsClickableNote get() = run { isClickableNote.value = this }

    val Boolean.setTextIsEmpty get() = run { textIsEmpty.value = this }

    private val Boolean.setNotShareVisible get() = run { notShareVisible.value = this }

    val Pair<Int, Int>.setSelection get() = run { selection = this }

    val Boolean.setClipboardIsEmpty get() = run { clipboardIsEmpty.value = this }

    val Boolean.setSettingsVisible get() = run { settingsVisible.value = this }

    infix fun Color.setColor(spanType: SpanType) = when (spanType) {
        is SpanType.Background -> setColorButtonBackground
        is SpanType.Foreground -> setColorButtonForeground
        is SpanType.Underlined -> Green.setColorButtonUnderlined
        is SpanType.Bold -> Green.setColorButtonBold
        is SpanType.Italic -> Green.setColorButtonItalic
        is SpanType.StrikeThrough -> Green.setColorButtonStrikeThrough
        is SpanType.Relative -> Unit
        is SpanType.Url -> Green.setColorButtonClick
    }

    fun <T> EditTextController.setAutoColor(type: SpanType, list: List<T>) {
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
                is SpanType.Relative -> Unit
                is SpanType.Url -> Green.setColorButtonClick
            }
    }

    fun setButtonWhite(spanType: SpanType) = with(White) {
        when (spanType) {
            is SpanType.Background -> setColorButtonBackground
            is SpanType.Foreground -> setColorButtonForeground
            is SpanType.Underlined -> setColorButtonUnderlined
            is SpanType.Bold -> setColorButtonBold
            is SpanType.Italic -> setColorButtonItalic
            is SpanType.StrikeThrough -> setColorButtonStrikeThrough
            is SpanType.Relative -> Unit
            is SpanType.Url -> setColorButtonClick
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
        setColorButtonClick

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

    private fun hideFormatPanel() = with(false) {
        setIsFormatMode
        setColorPickerBackgroundIsShow
        setColorPickerForegroundIsShow
    }

    fun onClickEditText() {
        hideFormatPanel()
        false.setAllColorPickerIsShow
        false.setIsSelected
    }

    fun setDeleteMode() {
        true.setIsDeleteMode
        false.setIsMainMode
        false.setIsClickableNote
        listDeleteAble.clear()
    }

    fun setMainMode() {
        false.setIsFullscreenMode
        (!getSettingsVisible).setIsMainMode
        (!getIsDeleteMode).setIsMainMode
        false.setIsExpandShare
        true.setNotShareVisible
    }

    fun setFullScreenMode() {
        true.setIsFullscreenMode
        false.setIsMainMode
    }

    fun addToDeleteAbleList(id: String) {
        listDeleteAble.add(id)
    }

    fun removeFromDeleteAbleList(id: String) {
        listDeleteAble.remove(id)
    }

    fun cancelDeleteMode() {
        listDeleteAble.clear()
        false.setIsDeleteMode
        true.setIsMainMode
        CoroutineScope(IO).launch {
            delay(300)
            true.setIsClickableNote
        }
    }

    fun expandShare(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            if (getIsExpandShare) {
                false.setIsExpandShare
                delay(500)
                true.setNotShareVisible
            } else {
                false.setNotShareVisible
                delay(200)
                true.setIsExpandShare
            }
        }
    }

    fun SpanType.getColorPicker() = if (this is SpanType.Background)
        getColorPickerBackgroundIsShow else getColorPickerForegroundIsShow

    @Composable
    fun ImageVector.getScale(textIsEmpty: Boolean) = when (this) {
            Icons.Rounded.ContentPaste -> animScale(getClipboardIsEmpty)
            Icons.Rounded.SelectAll -> animScale(textIsEmpty)
            Icons.Rounded.ContentCopy -> animScale(getIsSelected && textIsEmpty)
            Icons.Rounded.CopyAll -> animScale(textIsEmpty)
            Icons.Rounded.ContentCut -> animScale(getIsSelected && textIsEmpty)
            else -> 0f
        }

    fun ImageVector.getSpanType() = when (this) {
            Icons.Rounded.FormatColorFill -> SpanType.Background(getColorButtonBackground.toArgb())
            Icons.Rounded.FormatColorText -> SpanType.Foreground(getColorButtonForeground.toArgb())
            Icons.Rounded.FormatUnderlined -> SpanType.Underlined
            Icons.Rounded.FormatBold -> SpanType.Bold
            Icons.Rounded.FormatItalic -> SpanType.Italic
            Icons.Rounded.FormatStrikethrough -> SpanType.StrikeThrough
            Icons.Rounded.AddLink -> SpanType.Url
            else -> SpanType.Bold
        }

    fun ImageVector.getTint() = when (this) {
            Icons.Rounded.FormatColorFill -> getColorButtonBackground
            Icons.Rounded.FormatColorText -> getColorButtonForeground
            Icons.Rounded.FormatUnderlined -> getColorButtonUnderlined
            Icons.Rounded.FormatBold -> getColorButtonBold
            Icons.Rounded.FormatItalic -> getColorButtonItalic
            Icons.Rounded.FormatStrikethrough -> getColorButtonStrikeThrough
            Icons.Rounded.AddLink -> getColorButtonClick
            else -> White
        }
}