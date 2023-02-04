package com.lm.notes.ui.bars

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lm.notes.data.local_data.ShareType
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.FullScreenIcon
import com.lm.notes.ui.cells.IconClipBoard
import com.lm.notes.ui.cells.ShareCanvasButton
import com.lm.notes.utils.animScale

@Composable
fun LandscapeBar() {
    with(mainDep) {
        with(notesViewModel.uiStates) {
            val scrollState = rememberScrollState()
            LocalDensity.current.apply {
                ShareCanvasButton(160.dp, ShareType.AsHtml, y = 190.dp - scrollState.value.toDp())
                ShareCanvasButton(195.dp, ShareType.AsTxt, y = 190.dp - scrollState.value.toDp())
                ShareCanvasButton(230.dp, y = 190.dp - scrollState.value.toDp())
                Canvas(
                    Modifier
                        .offset((-18).dp, 190.dp - scrollState.value.toDp())
                        .scale(
                            animScale(getIsFullscreenMode && getTextIsEmpty)
                        )
                ) { drawCircle(White, 16.dp.toPx(), Offset.Zero) }
            }
            Column(
                Modifier
                    .scale(animScale(!getIsMainMode))
                    .verticalScroll(scrollState)
                    .padding(bottom = 40.dp)
            ) {
                listIconsClipboard.forEach { im ->
                    IconClipBoard(im, getTextIsEmpty, getMainColor, 8.dp)
                }
                listIconsFullScreen.forEach { FullScreenIcon(it, getMainColor, 15.dp, 3.dp) }
            }

        }
    }
}