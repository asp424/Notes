package com.lm.notes.ui

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
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep
import com.lm.notes.utils.formatTimestamp
import com.lm.notes.utils.noRippleClickable

@Composable
fun Note(noteModel: NoteModel) {
    with(noteModel) {
        with(MainDep.mainDep) {
            Card(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .noRippleClickable {
                        notesViewModel.setFullscreenNoteModel(id)
                        navController.navigate("fullScreenNote")
                    }
                    .height(
                        if (textState.value.text.isNotEmpty())
                            75.dp else 60.dp
                    ), contentColor = Color.White,
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column() {
                        Text(
                            text = headerState.value, maxLines = 1,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            fontSize = 14.sp
                        )
                        if (textState.value.text.isNotEmpty()) {
                            Text(
                                text = textState.value.text, maxLines = 1,
                                fontSize = 12.sp, color = Color.Gray
                            )
                        }
                        Text(
                            text = formatTimestamp(timestampChangeState.value),
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
}