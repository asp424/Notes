package com.lm.notes.ui.cells

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Widgets
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.bars.listIconsFullScreen
import com.lm.notes.utils.animScale
import com.lm.notes.utils.noRippleClickable

@Composable
fun FullScreenIcon(source: ImageVector) {
    with(mainDep) {
        with(notesViewModel.uiStates) {
            val values = source.getFullScreenIconsValues(
                coroutine, noteAppWidgetController, notesViewModel.noteModelFullScreen.value
            )
            if (source == listIconsFullScreen[0]) Canvas(
                Modifier.offset(width - 126.dp, 0.dp).scale(
                    animScale(getIsFullscreenMode && getTextIsEmpty)
                )
                ) { drawCircle(getMainColor, 18.dp.toPx(), Offset.Zero) }
            Box(
                Modifier
                    .offset(width - source.x, 0.dp)
                    .scale(values.first)
            ) {
                Icon(
                    source, null,
                    Modifier.noRippleClickable(values.second), getSecondColor
                )
            }
        }
    }
}

private val ImageVector.x
    get() = when (this) {
        Icons.Rounded.Share -> 135.dp
        Icons.Rounded.Widgets -> 195.dp
        else -> 195.dp
    }


