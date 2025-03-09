package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.notes.di.compose.MainDep.mainDep

@Composable
fun Note(
    modifier: Modifier, time: String, notesText: String, header: String, id: String, i: Int
) {
    with(mainDep){
        with(notesViewModel.uiStates) {

            Card(
                modifier = modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(8.dp), border = getNoteCardBorder(id),
                elevation = getNoteCardElevation(id)
            ) {
                Box(Modifier.padding(10.dp), CenterStart) {
                    Column {
                        NoteCardText(header)
                        if (notesText.isNotEmpty()) {
                            Text(
                                text = notesText, maxLines = 1,
                                fontSize = 12.sp, color = Color.Gray
                            )
                        }
                        NoteCardText(time, false)
                    }
                    Box(Modifier.fillMaxSize().padding(top = 10.dp, end = 10.dp),
                        contentAlignment = BottomEnd) {
                        CanvasCircle(0.dp, 8f, 22.dp, getMainColor)
                        Text(i.toString(), Modifier.offset((-5).dp, 5.dp),
                            color = getSecondColor, fontSize = 16.sp, fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
