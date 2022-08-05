package com.lm.notes.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun FullScreenBar() {
    with(mainDep) {
        notesViewModel.noteModelFullScreen.collectAsState().value.apply {
            Box(modifier = Modifier.padding(end = 20.dp)) {
                Icon(
                    Icons.Rounded.Share, null, modifier = Modifier
                        .noRippleClickable {
                            filesProvider.apply {
                                share(saveText(timestampChangeState.value, textState.value.text))
                            }
                        }
                        .scale(
                            animateFloatAsState(
                                if (
                                    textState.value.text.isEmpty()
                                ) 0f else 1f, tween(350)
                            ).value
                        ), tint = Color.White
                )
            }
            Icon(
                Icons.Rounded.Delete, null, modifier = Modifier
                    .noRippleClickable {
                        notesViewModel.deleteNoteById(coroutine, id)
                        navController.navigate("mainList") {
                            popUpTo("mainList")
                        }
                    }, tint = Color.White
            )
        }
    }
}
