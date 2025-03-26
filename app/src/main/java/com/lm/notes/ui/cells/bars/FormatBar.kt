package com.lm.notes.ui.cells.bars

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.ColorPickers
import com.lm.notes.ui.cells.icons.IconFormat
import com.lm.notes.ui.cells.icons.icons_lists.listFormatBarIcons
import com.lm.notes.ui.core.SpanType
import com.lm.notes.utils.animDp

@Composable
fun FormatBar() {
    with(mainDep) {
        with(nVM.uiStates) {
            Card(
                Modifier
                    .offset(0.dp, animDp(getIsFormatMode, height - 45.dp, height))
                    .height(45.dp)
                    .fillMaxWidth(),
                RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, getSecondColor),
                colors = CardColors(getMainColor, Black, Black, Black)
            ) {
                Row(
                    Modifier.fillMaxSize(),
                    Arrangement.Center,
                    CenterVertically
                ) { listFormatBarIcons.forEach { IconFormat(it) } }
            }

            Box(Modifier, Center) {
                Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                    ColorPickers(listOf(SpanType.Foreground(0), SpanType.Background(0)))
                }
            }
        }
    }
}

