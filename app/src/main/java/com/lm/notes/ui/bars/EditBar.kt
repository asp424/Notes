package com.lm.notes.ui.bars

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep
import com.lm.notes.ui.cells.ColorPickers
import com.lm.notes.ui.cells.IconFormat
import com.lm.notes.ui.core.SpanType
import com.lm.notes.ui.theme.back
import com.lm.notes.utils.animDp

@Composable
fun EditBar() {
    with(MainDep.mainDep) {
        with(notesViewModel.uiStates) {
            Card(
                Modifier
                    .offset(0.dp, animDp(getIsFormatMode, height - 45.dp, height))
                    .height(45.dp)
                    .padding(1.dp)
                    .fillMaxWidth(),
                RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, Color.Black), backgroundColor = back
            ) {
                Box(
                    Modifier.padding(10.dp), Alignment.Center
                ) {
                    Row(
                        Modifier,
                        Arrangement.Start,
                        Alignment.Bottom
                    ) { listIcons.forEach { IconFormat(it) } }
                }
            }

            Box(Modifier, Alignment.Center) {
                Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                    ColorPickers(listOf(SpanType.Foreground(0), SpanType.Background(0)))
                }
            }
        }
    }
}