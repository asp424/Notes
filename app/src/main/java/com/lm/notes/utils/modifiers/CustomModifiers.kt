package com.lm.notes.utils.modifiers

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.lm.notes.data.models.NavControllerScreens
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep.mainDep


inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    composed {
        clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
    }

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.paddingInt(start: Int = 0, top: Int = 0, end: Int = 0, bottom: Int = 0): Modifier =
    composed {
        padding(start.dp, top.dp, end.dp, bottom.dp)
    }

fun Modifier.noteClickable(nM: NoteModel) = composed {
    with(mainDep) {
        with(nVM) {
            with(uiStates) {
                with(nM) {
                    pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                with(nVM) {
                                    if (getIsDeleteMode) {
                                        if (listDeleteAble.contains(id)) {
                                            removeFromDeleteAbleList(id)
                                            if (listDeleteAble.isEmpty()) {
                                                cancelDeleteMode()
                                            }
                                        } else addToDeleteAbleList(id)
                                    }
                                    if (!getIsDeleteMode && getIsClickableNote) {
                                        NavControllerScreens.Note.setNavControllerScreen
                                        setFullscreenNoteModel(nM)
                                        with(editTextController) {
                                            createEditText()
                                            setNewText(text)
                                            editText.post {
                                                editText.lineCount.setLinesCounter
                                            }
                                        }
                                        false.setTranslateEnable
                                        checkForEmptyText()
                                        false.setIsSelected
                                    }
                                }
                            },
                            onLongPress = { setDeleteMode(); addToDeleteAbleList(id) })
                    }
                }
            }
        }
    }
}