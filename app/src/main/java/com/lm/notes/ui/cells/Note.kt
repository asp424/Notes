package com.lm.notes.ui.cells

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
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
import com.lm.notes.di.compose.MainDep.mainDep

@Composable
fun Note(modifier: Modifier, time: String, notesText: String, header: String, id: String) {
    with(mainDep.notesViewModel.uiStates) {
        Card(
            modifier = modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight(), contentColor = Color.White,
            shape = RoundedCornerShape(8.dp), border =
            if (listDeleteAble.contains(id) && getIsDeleteMode) BorderStroke(2.dp, Color.Red)
            else BorderStroke(1.dp, getMainColor),
            elevation = if (listDeleteAble.contains(id) && getIsDeleteMode) 50.dp else 10.dp
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
                            text = "$notesText...", maxLines = 1,
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
}