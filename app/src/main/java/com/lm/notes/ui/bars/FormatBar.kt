package com.lm.notes.ui.bars

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Start
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.ColorPickers
import com.lm.notes.ui.cells.IconFormat
import com.lm.notes.ui.cells.view.SpanType
import com.lm.notes.ui.theme.back
import com.lm.notes.utils.animDp
import com.lm.notes.utils.log

@Composable
fun FormatBar() {
    with(mainDep) {
        with(notesViewModel.uiStates) {
            Card(
                Modifier
                    .offset(0.dp, animDp(getIsFormatMode, height - 45.dp, height, 100))
                    .height(45.dp).padding(1.dp).fillMaxWidth(),
                RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, Color.Black), backgroundColor = back
            ) {
                Box(
                    Modifier.padding(10.dp), Center
                ) {
                    Row(Modifier, Start, Bottom) { listIcons.forEach { IconFormat(it) } }
                }
            }

            Box(Modifier, Center) {
                Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                    ColorPickers(listOf(SpanType.Foreground(0), SpanType.Background(0)))
                }
            }
        }
    }
}

val listIcons
    get() = listOf(
        Icons.Rounded.AddLink,
        Icons.Rounded.FormatBold,
        Icons.Rounded.FormatItalic,
        Icons.Rounded.FormatUnderlined,
        Icons.Rounded.FormatStrikethrough,
        Icons.Rounded.FormatColorText,
        Icons.Rounded.FormatColorFill,
        Icons.Rounded.FormatClear
    )
