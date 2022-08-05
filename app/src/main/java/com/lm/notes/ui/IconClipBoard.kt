package com.lm.notes.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.ContentCut
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.material.icons.rounded.SelectAll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun IconClipBoard(
    source: ImageVector,
    visibility: Boolean,
    color: Color = White
) {
    with(mainDep) {
        with(clipboardProvider) {
            notesViewModel.noteModelFullScreen.collectAsState().value.apply {

                Box(modifier = Modifier.padding(start = 10.dp)) {
                    Icon(
                        source, null,
                        modifier = Modifier
                            .noRippleClickable {
                                when (source) {
                                    Icons.Rounded.ContentPaste -> paste()
                                    Icons.Rounded.SelectAll -> selectAll()
                                    Icons.Rounded.ContentCopy -> copySelected()
                                    Icons.Rounded.ContentCut -> cutSelected()
                                }
                            }
                            .scale(
                                animateFloatAsState(
                                    if (visibility) 1f else 0f, tween(350)
                                ).value
                            ),
                        tint = color
                    )
                }
            }
        }
    }
}
