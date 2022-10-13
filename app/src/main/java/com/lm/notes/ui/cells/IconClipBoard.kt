package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.MainActivity
import com.lm.notes.utils.animScale
import com.lm.notes.utils.longToast
import com.lm.notes.utils.noRippleClickable

@Composable
fun IconClipBoard(source: ImageVector, textIsEmpty: Boolean) {
    with(mainDep) {
        with(notesViewModel) {
            val activity = LocalContext.current as MainActivity
            with(clipboardProvider) {
                with(uiStates) {
                    val scale = when (source) {
                        Icons.Rounded.ContentPaste -> animScale(getClipboardIsEmpty)
                        Icons.Rounded.SelectAll -> {
                            animScale(textIsEmpty)
                        }
                        Icons.Rounded.ContentCopy -> animScale(getIsSelected && textIsEmpty)
                        Icons.Rounded.CopyAll -> animScale(textIsEmpty)
                        Icons.Rounded.ContentCut -> animScale(getIsSelected && textIsEmpty)
                        else -> 0f
                    }
                    val click = remember {
                        {
                            when (source) {
                                Icons.Rounded.ContentPaste -> paste()
                                Icons.Rounded.SelectAll -> selectAll()
                                Icons.Rounded.ContentCopy -> {
                                    copySelected()
                                    activity.longToast("Selected text was copied to clipboard")
                                }
                                Icons.Rounded.CopyAll -> {
                                    copyAll()
                                    activity.longToast("All text was copied to clipboard")
                                }
                                Icons.Rounded.ContentCut -> cutSelected()
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(
                                start = 5.dp, end =
                                if (source == Icons.Rounded.ContentPaste) 4.dp else 0.dp
                            )
                            .size(scale.dp * 30)
                    ) {
                        Icon(
                            source, null,
                            modifier = Modifier
                                .noRippleClickable(click)
                                .scale(scale),
                            tint = White
                        )
                    }
                }
            }
        }
    }
}
