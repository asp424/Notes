package com.lm.notes.ui.bars

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lm.notes.data.models.NoteModel
import com.lm.notes.ui.cells.IconFormat
import com.lm.notes.utils.animDp
import com.lm.notes.utils.animScale

@Composable
fun FormatBar(isFormatMode: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(animDp(isFormatMode, 20.dp, 0.dp, 100)),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset (0.dp, animDp(isFormatMode, first = 0.dp, second = 60.dp, 100))
                .padding(1.dp),
            border = BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(20.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Row {

                        IconFormat(Icons.Rounded.FormatBold)

                        IconFormat(Icons.Rounded.FormatItalic)

                        IconFormat(Icons.Rounded.FormatUnderlined)

                        IconFormat(Icons.Rounded.FormatColorText)

                        IconFormat(Icons.Rounded.FormatColorFill)
                    }
                }
            }
        }
    }
}
