package com.lm.notes.ui.cells

import android.content.res.Configuration
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun IconClipBoard(
    source: ImageVector,
    textIsEmpty: Boolean,
    color: Color,
    bottomPadding: Dp = 0.dp
) {
    with(mainDep.notesViewModel) {
        with(uiStates) {
            val scale = source.getScale(textIsEmpty)
            val configuration = LocalConfiguration.current
            Box(
                Modifier
                    .padding(
                        start = if (source == Icons.Rounded.ContentPaste &&
                            configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 2.dp else 5.dp,
                        end = 0.dp,
                        bottom = if (source == Icons.Rounded.ContentPaste &&
                                configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                                ) 10.dp + bottomPadding else bottomPadding
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
                                .size(20.dp), color
                        )
                        Text(
                            text = getPasteIconLabel,
                            color = color, fontSize = 8.sp
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
                        .scale(scale), color
                )
            }
        }
    }
}
