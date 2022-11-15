package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun IconClipBoard(source: ImageVector, textIsEmpty: Boolean) {
    with(mainDep.notesViewModel) {
        with(uiStates) {
            val scale = source.getScale(textIsEmpty)
            Box(
                Modifier
                    .padding(
                        start = 5.dp, end =
                        if (source == Icons.Rounded.ContentPaste) 4.dp else 0.dp
                    )
                    .size(scale.dp * 30)
            ) {
                if (source == Icons.Rounded.ContentPaste)
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .scale(scale),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            source, null,
                            Modifier
                                .noRippleClickable(remember {
                                    { with(clipboardProvider) { source.clickOnButtonsClipboard() } }
                                })
                                .size(20.dp), getSecondColor
                        )
                        Text(
                            text = getPasteIconLabel,
                            color = getSecondColor, fontSize = 8.sp
                        )
                    } else Icon(
                    source, null,
                    Modifier
                        .noRippleClickable(
                            remember {
                                {
                                    with(clipboardProvider) { source.clickOnButtonsClipboard() }
                                }
                            })
                        .scale(scale), getSecondColor
                )
            }
        }
    }
}
