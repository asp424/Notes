package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.animScaleDynamic
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
                    ).offset((-20).dp, 0.dp)
                        .scale(animScaleDynamic(notesViewModel.isAuth, 1.2f, 0f)),
                    tint = getSecondColor
                )
            }
        }
    }
}