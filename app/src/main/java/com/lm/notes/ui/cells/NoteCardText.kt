package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NoteCardText(text: String, isBold: Boolean = true) {
    Text(
        text = text,
        maxLines = 1,
        fontSize = if (!isBold) 10.sp else 14.sp,
        fontStyle = if (!isBold) FontStyle.Italic else FontStyle.Normal,
        style = TextStyle(fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal),
        modifier = if (!isBold) Modifier.padding(top = 4.dp) else Modifier
    )
}