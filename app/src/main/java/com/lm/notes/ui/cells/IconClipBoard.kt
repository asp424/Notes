package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
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
                Icon(
                    source, null,
                    Modifier
                        .noRippleClickable(remember {
                            { with(clipboardProvider) { source.clickOnButtonsClipboard() } }
                        })
                        .scale(scale), getSecondColor
                )
            }
        }
    }
}
