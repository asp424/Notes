package com.lm.notes.ui.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatBold
import androidx.compose.material.icons.rounded.FormatItalic
import androidx.compose.material.icons.rounded.FormatUnderlined
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.format_text.TextFormatter.Base.Companion.BOLD
import com.lm.notes.utils.format_text.TextFormatter.Base.Companion.ITALIC
import com.lm.notes.utils.format_text.TextFormatter.Base.Companion.UNDERLINE
import com.lm.notes.utils.noRippleClickable

@Composable
fun IconFormat(source: ImageVector) {
    with(mainDep) {
        with(mainDep.textFormatter) {
            notesViewModel.noteModelFullScreen.collectAsState().value.apply {
                Box(modifier = Modifier.padding(start = 10.dp)) {
                    Icon(
                        source, null,
                        modifier = Modifier
                            .noRippleClickable {
                                when (source) {
                                    Icons.Rounded.FormatBold ->
                                        translate(boldMap, BOLD)
                                    Icons.Rounded.FormatItalic ->
                                        translate(italicMap, ITALIC)
                                    Icons.Rounded.FormatUnderlined ->
                                        translate(underlinedMap, UNDERLINE)
                                }
                            }
                            .scale(
                                animateFloatAsState(
                                    if (textState.value.selection.length != 0) 1f else 0f,
                                    tween(350)
                                ).value
                            ),
                        tint = when (source) {
                            Icons.Rounded.FormatBold ->
                                getColor(boldMap.value, BOLD)
                            Icons.Rounded.FormatItalic ->
                                getColor(italicMap.value, ITALIC)
                            Icons.Rounded.FormatUnderlined ->
                                getColor(underlinedMap.value, UNDERLINE)
                            else -> White
                        }
                    )
                }
            }
        }
    }
}
