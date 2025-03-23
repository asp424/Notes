package com.lm.notes.ui.cells.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun DeleteForeverIcon() {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                Icon(
                    Icons.Rounded.DeleteForever,
                    null,
                    modifier = Modifier.noRippleClickable(
                        remember {
                            {
                                listDeleteAble.forEach {
                                    deleteNote(it)
                                    deleteNoteFromFirebase(it)
                                }
                                cancelDeleteMode()
                            }
                        }
                    ), tint = getSecondColor
                )
            }
        }
    }
}