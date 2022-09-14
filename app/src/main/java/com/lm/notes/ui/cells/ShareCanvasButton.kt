package com.lm.notes.ui.cells

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lm.notes.utils.noRippleClickable

@Composable
fun ShareCanvasButton(
    x: Dp, click: () -> Unit,
    isExpand: MutableState<Boolean>,
    scale: Float, paint: NativePaint,
    text: String,
    textX: Float,
    textS: Float
) {

    Canvas(
        Modifier
            .offset(x, 0.dp)
            .noRippleClickable {
                click()
                isExpand.value = false
            }.scale(scale)
    ) {
        drawCircle(Color.White, 15.dp.toPx(), Offset.Zero)
        paint.apply { textSize = textS }
        drawIntoCanvas { it.nativeCanvas.drawText(text, textX, 15f, paint) }
    }
}
