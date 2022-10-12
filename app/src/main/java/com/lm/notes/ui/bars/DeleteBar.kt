package com.lm.notes.ui.bars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun DeleteBar(animScale: Float) {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                Box(
                    Modifier
                        .offset(width - 215.dp, 0.dp)
                        .scale(animScale)
                ) {

                    Icon(
                        Icons.Rounded.Delete,
                        null,
                        modifier = Modifier.noRippleClickable {
                            listDeleteAble.forEach { deleteNote(it) }
                            setMainMode()
                        },
                        tint = Color.White
                    )
                }
                Box(
                    Modifier
                        .offset(width - 200.dp, 0.dp)
                        .scale(animScale)
                ) {
                    Icon(
                        Icons.Rounded.Close,
                        null,
                        modifier = Modifier.noRippleClickable { setMainMode() },
                        tint = Color.White
                    )
                }
            }
        }
    }
}