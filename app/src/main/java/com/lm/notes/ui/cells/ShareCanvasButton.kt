package com.lm.notes.ui.cells

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun ShareCanvasButton(
    x: Dp, click: () -> Unit,
    scale: Float, paint: NativePaint,
    text: String,
    textX: Float,
    textS: Float
) {
    with(mainDep.notesViewModel.uiStates) {
        Canvas(
            Modifier
                .offset(x, 0.dp)
                .noRippleClickable {
                    click()
                    false.setIsExpandShare
                }
                .scale(scale)
        ) {
            drawCircle(Color.White, 15.dp.toPx(), Offset.Zero)
            paint.apply { textSize = textS }
            drawIntoCanvas { it.nativeCanvas.drawText(text, textX, 15f, paint) }
        }
    }
}
