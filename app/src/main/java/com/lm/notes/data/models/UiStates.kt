package com.lm.notes.data.models

import android.annotation.SuppressLint
import android.content.Intent
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddLink
import androidx.compose.material.icons.rounded.ClearAll
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.FormatBold
import androidx.compose.material.icons.rounded.FormatClear
import androidx.compose.material.icons.rounded.FormatColorFill
import androidx.compose.material.icons.rounded.FormatColorText
import androidx.compose.material.icons.rounded.FormatItalic
import androidx.compose.material.icons.rounded.FormatStrikethrough
import androidx.compose.material.icons.rounded.FormatUnderlined
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.SelectAll
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Translate
import androidx.compose.material.icons.rounded.Widgets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import com.lm.notes.data.local_data.NoteData.Base.Companion.NEW_TAG
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.di.compose.animVisibility
import com.lm.notes.presentation.MainActivity
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.ui.cells.view.EditTextController
import com.lm.notes.ui.cells.view.app_widget.NoteAppWidgetController
import com.lm.notes.ui.core.SpanType
import com.lm.notes.ui.theme.main
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Immutable
@Stable
data class UiStates(
    private var isFormatMode: MutableState<Boolean> = mutableStateOf(false),
    private var translateEnable: MutableState<Boolean> = mutableStateOf(false),
    private var linesCounter: MutableState<Int> = mutableIntStateOf(1),
    private var pasteIconLabel: MutableState<String> = mutableStateOf(""),
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
    private var isNoteMode: MutableState<Boolean> = mutableStateOf(false),
    private var isMainMode: MutableState<Boolean> = mutableStateOf(true),
    private var isExpandShare: MutableState<Boolean> = mutableStateOf(false),
    private var settingsVisible: MutableState<Boolean> = mutableStateOf(false),
    private var notShareVisible: MutableState<Boolean> = mutableStateOf(true),
    private var textIsEmpty: MutableState<Boolean> = mutableStateOf(true),
    private var isReversLayout: MutableState<Boolean> = mutableStateOf(false),
    private var isClickableNote: MutableState<Boolean> = mutableStateOf(true),
    private var navControllerScreen: MutableState<NavControllerScreens>
    = mutableStateOf(NavControllerScreens.Main),
    var progressVisibility: MutableState<Boolean> = mutableStateOf(false),
    var authButtonMenuVisibility: MutableState<Boolean> = mutableStateOf(false),
    val listDeleteAble: SnapshotStateList<String> = mutableStateListOf(),
    val mainColor: MutableState<Color> = mutableStateOf(main),
    val secondColor: MutableState<Color> = mutableStateOf(main),
    var selection: Pair<Int, Int> = Pair(-1, -1)
) {
    val getIsFormatMode get() = isFormatMode.value
    val getNavControllerScreens get() = navControllerScreen.value
    val getTranslateEnable get() = translateEnable.value
    val getIsReversLayout get() = isReversLayout.value
    val getIsClickableNote get() = isClickableNote.value
    val getLinesCounter get() = linesCounter.value
    val getPasteIconLabel get() = pasteIconLabel.value
    val getSetSelectionEnable get() = setSelectionEnable.value
    val getTextIsEmpty get() = textIsEmpty.value
    val getMainColor get() = mainColor.value
    val getSecondColor get() = secondColor.value
    val getSettingsVisible get() = settingsVisible.value
    val getSelection get() = selection
    val getIsExpandShare get() = isExpandShare.value
    val getIsMainMode get() = isMainMode.value
    val getNoteMode get() = isNoteMode.value
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
    private val Boolean.setIsFormatMode get() = run { isFormatMode.value = this }
    private val Boolean.setIsDeleteMode get() = run { isDeleteMode.value = this }
    private val Boolean.setIsNodeMode get() = run { isNoteMode.value = this }
    private val Boolean.setIsMainMode get() = run { isMainMode.value = this }
    private val Boolean.setIsExpandShare get() = run { isExpandShare.value = this }
    private val Boolean.setIsClickableNote get() = run { isClickableNote.value = this }
    private val Boolean.setReversLayout get() = run { isReversLayout.value = this }
    val NavControllerScreens.setNavControllerScreen get() = run { navControllerScreen.value = this }
    private val Boolean.setColorPickerBackgroundIsShow
        get() = run {
            colorPickerBackgroundIsShow.value = this
        }
    private val Boolean.setColorPickerForegroundIsShow
        get() = run {
            colorPickerForegroundIsShow.value = this
        }

    private val Color.setColorButtonBackground get() = run { colorButtonBackground.value = this }

    val Int.setLinesCounter get() = run { linesCounter.value = this }
    private val Color.setColorButtonForeground get() = run { colorButtonForeground.value = this }
    private val Color.setColorButtonUnderlined get() = run { colorButtonUnderlined.value = this }
    private val Color.setColorButtonBold get() = run { colorButtonBold.value = this }
    private val Color.setColorButtonItalic get() = run { colorButtonItalic.value = this }
    private val Color.setColorButtonClick get() = run { colorButtonClick.value = this }
    val Color.setMainColor get() = run { mainColor.value = this }
    val Color.setSecondColor get() = run { secondColor.value = this }
    val String.setPasteIconLabel
        get() = run {
            pasteIconLabel.value = if (startsWith("text")) "text"
            else this
        }
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

    val Boolean.setTranslateEnable get() = run { translateEnable.value = this }

    private val Boolean.setSetSelectionEnable get() = run { setSelectionEnable.value = this }

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
        is SpanType.Clear -> Unit
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
                else -> Unit
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
            is SpanType.Clear -> Unit
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

    fun hideFormatPanel() {
        false.setIsFormatMode
        false.setColorPickerBackgroundIsShow
        false.setColorPickerForegroundIsShow
    }

    fun onClickEditText() {
        hideFormatPanel()
        false.setAllColorPickerIsShow
        false.setIsSelected
    }

    fun setReversLayout() {
        if (getIsReversLayout) false.setReversLayout
        else true.setReversLayout
    }

    fun setDeleteMode() {
        true.setIsDeleteMode
        false.setIsMainMode
        false.setIsClickableNote
        false.setIsNodeMode
        listDeleteAble.clear()
    }

    suspend fun setMainMode() {
        false.setIsNodeMode
        false.setIsExpandShare
        true.setNotShareVisible
        delay(100)
        (!getSettingsVisible).setIsMainMode
        (!getIsDeleteMode).setIsMainMode
    }

    suspend fun setFullScreenMode() {
        false.setIsMainMode
        delay(100)
        true.setIsNodeMode
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
            delay(200)
            true.setIsClickableNote
        }
    }

    fun expandShare(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            if (getIsExpandShare) {
                false.setIsExpandShare
                delay(200)
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

    @SuppressLint("ModifierFactoryUnreferencedReceiver")
    @Composable
    fun Modifier.visibility(icon: ImageVector, textIsEmpty: Boolean): Modifier = with(mainDep) {
        when (icon) {
            Icons.Rounded.ContentPaste -> iconVisibility(getClipboardIsEmpty)
            Icons.Rounded.SelectAll -> iconVisibility(textIsEmpty)
            Icons.Rounded.CopyAll -> iconVisibility(textIsEmpty)
            Icons.Rounded.ClearAll -> iconVisibility(getClipboardIsEmpty)
            else -> iconVisibility(getIsSelected && textIsEmpty)
        }
    }

    @Composable
    fun animScale(target: Boolean, duration: Int = 300) = animateFloatAsState(
        if (target) 1f else 0f, tween(duration)
    ).value

    @Composable
    fun ImageVector.getScale(textIsEmpty: Boolean) =
        when (this) {
            Icons.Rounded.ContentPaste -> animScale(getClipboardIsEmpty)
            Icons.Rounded.SelectAll -> animScale(textIsEmpty)
            Icons.Rounded.CopyAll -> animScale(textIsEmpty)
            Icons.Rounded.ClearAll -> animScale(getClipboardIsEmpty)
            else -> animScale(getIsSelected && textIsEmpty)
        }

    @Composable
    fun ImageVector.getNoteBarIconsActions(
        coroutine: CoroutineScope,
        noteAppWidgetController: NoteAppWidgetController,
        noteModel: NoteModel,
        editTextController: EditTextController,
        @SuppressLint("ContextCastToActivity")
        activity: MainActivity = LocalContext.current as MainActivity,
        animation: Float =
            animVisibility(getNoteMode && getTextIsEmpty)
    ) = when (this@getNoteBarIconsActions) {
        Icons.Rounded.Share -> Pair(animation, remember { { expandShare(coroutine) } })

        Icons.Rounded.Widgets -> Pair(
            animation, remember(noteModel) {
                { noteAppWidgetController.pinNoteWidget(noteModel.id) }
            }
        )

        Icons.Rounded.Translate -> Pair(
            animation, remember { { editTextController.findEnglish() } })

        Icons.Rounded.Save -> Pair(
            animation, remember(noteModel) {
                {
                    activity.chooseFolderPath.launch(Intent(Intent.ACTION_CREATE_DOCUMENT)
                        .apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            type = "application/txt"
                            putExtra(
                                Intent.EXTRA_TITLE, "${
                                    if (noteModel.header.startsWith(NEW_TAG))
                                        noteModel.header.substringAfter(NEW_TAG) else noteModel.header
                                }.txt"
                            )
                        }
                    )
                }
            }
        )

        else -> Pair(0f) {}
    }

    @Composable
    fun ImageVector.getDeleteBarIconsActions(
        deleteAction: () -> Unit =
            with(mainDep.nVM) {
                remember { { listDeleteAble.forEach { deleteNote(it) }; cancelDeleteMode() } }
            },
        deleteForeverAction: () -> Unit = with(mainDep.nVM) {
            remember {
                {
                    listDeleteAble.forEach { deleteNote(it);deleteNoteFromFirebase(it) }
                    cancelDeleteMode()
                }
            }
        }
    ): () -> Unit = when (this) {
        Icons.Rounded.Delete -> deleteAction
        Icons.Rounded.DeleteForever -> deleteForeverAction
        else -> {{}}
    }

    fun ImageVector.getButtonFormatValues() = when (this) {
        Icons.Rounded.FormatColorFill -> with(getColorButtonBackground) {
            Pair(this, SpanType.Background(toArgb()))
        }

        Icons.Rounded.FormatColorText -> with(getColorButtonForeground) {
            Pair(this, SpanType.Foreground(toArgb()))
        }

        Icons.Rounded.FormatUnderlined -> Pair(getColorButtonUnderlined, SpanType.Underlined)
        Icons.Rounded.FormatBold -> Pair(getColorButtonBold, SpanType.Bold)
        Icons.Rounded.FormatItalic -> Pair(getColorButtonItalic, SpanType.Italic)
        Icons.Rounded.FormatStrikethrough -> Pair(
            getColorButtonStrikeThrough,
            SpanType.StrikeThrough
        )

        Icons.Rounded.AddLink -> Pair(getColorButtonClick, SpanType.Url)
        Icons.Rounded.FormatClear -> Pair(White, SpanType.Clear)
        else -> Pair(White, SpanType.Italic)
    }

    fun setFormat() {
        true.setIsFormatMode
        true.setIsSelected
    }

    fun getNoteCardBorder(id: String) = if (checkNoteCardMode(id)) BorderStroke(3.dp, Color.Red)
    else BorderStroke(2.dp, getMainColor)

    fun getNoteCardElevation(id: String) = if (checkNoteCardMode(id)) 50.dp else 10.dp

    private fun checkNoteCardMode(id: String) = listDeleteAble.contains(id) && getIsDeleteMode

    fun setSelection(scope: LifecycleCoroutineScope, notesViewModel: NotesViewModel) {
        scope.launch {
            notesViewModel.editTextController.editText.isEnabled = true
            false.setSetSelectionEnable
            false.setTranslateEnable
            notesViewModel.editTextController.setSelection()
            delay(300)
            true.setSetSelectionEnable
            notesViewModel.clipboardProvider.checkForEmpty()
            progressVisibility
        }
    }

    val settingsIconClick
        @Composable get() = remember {
            {
                if (getSettingsVisible) false.setSettingsVisible else true.setSettingsVisible
            }
        }

    @Composable
    fun NavControllerController() = with(mainDep) {
        when (getNavControllerScreens) {
            NavControllerScreens.Main -> navController.navigate(NavControllerScreens.Main.screen)
            NavControllerScreens.Note -> navController.navigate(NavControllerScreens.Note.screen)
            { popUpTo(NavControllerScreens.Main.screen) }
        }
    }
}

