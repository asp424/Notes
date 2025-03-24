package com.lm.notes.ui.cells.bars

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lm.notes.data.local_data.ShareType
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.ShareCanvasButton
import com.lm.notes.ui.cells.icons.IconClipBoard
import com.lm.notes.utils.listIconsClipboard
import com.lm.notes.utils.listIconsNote

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun LandscapeBar() {
    with(mainDep) {
        with(notesViewModel.uiStates) {
            val scrollState = rememberScrollState()
            Row {
                LocalDensity.current.apply {
                    ShareCanvasButton(
                        160.dp,
                        ShareType.AsHtml,
                        y = 10.dp
                    )
                    ShareCanvasButton(
                        195.dp,
                        ShareType.AsTxt,
                        y = 10.dp
                    )
                    ShareCanvasButton(230.dp, y = 10.dp)

                    Canvas(
                        Modifier
                            .iconVisibility(getNoteMode && getTextIsEmpty)
                            .offset((-18).dp, 10.dp)
                    ) { drawCircle(White, 16.dp.toPx(), Offset.Zero) }
                }
            }
            Column(
                Modifier
                    .iconVisibility(!getIsMainMode)
                    .verticalScroll(scrollState)
                    .padding(bottom = 40.dp)
                    .scale(0.8f)
            ) {
                listIconsNote.forEach {
                   // NoteBarIcon(it, getMainColor, 3.dp, 0.dp)
                }
                Column(Modifier.offset((-5).dp, 0.dp)) {
                    listIconsClipboard.forEach { im ->
                        IconClipBoard(im, getTextIsEmpty, getMainColor, 2.dp)
                    }
                }
            }
        }
    }
}