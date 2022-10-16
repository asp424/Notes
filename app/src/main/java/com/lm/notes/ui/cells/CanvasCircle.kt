package com.lm.notes.ui.cells

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CanvasCircle(x: Dp, scale: Float, radius: Dp, color: Color) {
    Canvas(
        Modifier
            .offset(x, 0.dp)
            .scale(scale)
    ) { drawCircle(color, radius.toPx() / density, Offset.Zero) }
}