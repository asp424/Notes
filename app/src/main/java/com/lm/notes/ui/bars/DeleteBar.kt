package com.lm.notes.ui.bars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.DeleteForeverIcon
import com.lm.notes.ui.cells.DeleteIcon

@Composable
fun DeleteBar(animScale: Float) {
    with(mainDep) {
        Box(
            Modifier
                .offset(width - 300.dp, 0.dp)
                .scale(animScale)
        ) {
            Row(Modifier.offset((-30).dp, 0.dp).fillMaxWidth()) {
                DeleteForeverIcon()
                DeleteIcon()
            }
        }
    }
}