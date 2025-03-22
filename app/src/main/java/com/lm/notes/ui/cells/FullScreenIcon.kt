package com.lm.notes.ui.cells

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Translate
import androidx.compose.material.icons.rounded.Widgets
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.di.compose.animVisibility
import com.lm.notes.ui.bars.listIconsFullScreen
import com.lm.notes.utils.noRippleClickable

@Composable
fun FullScreenIcon(
    source: ImageVector, color: Color,
    bottomPadding: Dp = 0.dp,
    startPadding: Dp = 0.dp
) {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                val configuration = LocalConfiguration.current
                val values = source.getFullScreenIconsValues(
                    coroutine, noteAppWidgetController, notesViewModel.noteModelFullScreen.value,
                    editTextController
                )
                if (source == listIconsFullScreen[0]) {
                    Canvas(
                        Modifier
                            .offset(width - 126.dp, 10.dp)
                            .scale(
                                if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                                    animVisibility(getIsFullscreenMode && getTextIsEmpty) else
                                0f
                            )
                    ) { drawCircle(getMainColor, 18.dp.toPx(), Offset.Zero) }
                }
                Box(
                    Modifier
                        .offset(
                            if (configuration.orientation
                                == Configuration.ORIENTATION_PORTRAIT
                            )
                                (width - source.x) else 0.dp, 0.dp
                        )
                        .scale(values.first)
                        .padding(bottom = bottomPadding, start = startPadding)
                ) {
                    Icon(
                        source, null,
                        Modifier.noRippleClickable(values.second), color
                    )
                }
            }
        }
    }
}

private val ImageVector.x
    get() = when (this) {
        Icons.Rounded.Share -> 135.dp
        Icons.Rounded.Widgets -> 195.dp
        Icons.Rounded.Translate -> 255.dp
        Icons.Rounded.Save -> 315.dp
        else -> 195.dp
    }


