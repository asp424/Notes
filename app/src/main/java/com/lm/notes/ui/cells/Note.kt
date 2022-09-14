package com.lm.notes.ui.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Note(modifier: Modifier, time: String, notesText: String, header: String) {

    Card(
        modifier = modifier, contentColor = Color.White,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            Modifier.padding(10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = header,
                    maxLines = 1,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = 14.sp
                )
                if (notesText.isNotEmpty()) {
                    Text(
                        text = notesText, maxLines = 1,
                        fontSize = 12.sp, color = Color.Gray
                    )
                }
                Text(
                    text = time,
                    maxLines = 1,
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
