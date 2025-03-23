package com.lm.notes.ui.cells.icons

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun DeleteBarIcon(icon: ImageVector) {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                Icon(
                    icon, null, Modifier
                        .noRippleClickable(
                            remember {
                                {
                                    listDeleteAble.forEach {
                                        deleteNote(it)
                                        deleteNoteFromFirebase(it)
                                    }
                                    cancelDeleteMode()
                                }
                            }
                        ).iconVisibility(getIsDeleteMode), tint = getSecondColor
                )
            }
        }
    }
}