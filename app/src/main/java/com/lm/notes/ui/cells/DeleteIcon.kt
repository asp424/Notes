package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun DeleteIcon() {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                Icon(
                    Icons.Rounded.Delete,
                    null,
                    modifier = Modifier.noRippleClickable(
                        remember {
                            {
                                listDeleteAble.forEach {
                                    deleteNote(it)
                                }
                                cancelDeleteMode()
                            }
                        }
                    ).scale(1.2f),
                    tint = getSecondColor
                )
            }
        }
    }
}