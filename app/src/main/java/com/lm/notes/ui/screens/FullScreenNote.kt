package com.lm.notes.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.End
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.dp
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.bars.ClipboardBar
import com.lm.notes.ui.cells.EditText
import com.lm.notes.ui.cells.HeaderTextField
import com.lm.notes.ui.theme.ass

@Composable
fun FullScreenNote(
    noteModel: NoteModel
) {
    with(mainDep.notesViewModel.uiStates) {
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize()) {
                ClipboardBar()
                HeaderTextField(noteModel)
                EditText()
            }
            // Visibility(getPaintVisibility) {
            //Paint()
            //}
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Paint() {
    with(mainDep) {
        Column(
            Modifier
                .fillMaxHeight()
                .width(width + 250.dp)
                .offset(0.dp, 100.dp)
        ) {
            remember { mutableStateListOf<Offset>() }.apply {

                Card(
                    Modifier
                        .width(width + 250.dp)
                        .fillMaxHeight()
                        .motionEventSpy { add(Offset(it.x, it.y - 450f)) },
                    RoundedCornerShape(20.dp),
                    backgroundColor = ass
                ) {

                    Canvas(Modifier) { forEach { drawCircle(Black, 15f, it) } }
                }
                Row(
                    Modifier
                        .padding(end = 40.dp, top = 20.dp)
                        .fillMaxWidth(), End
                ) {
                    Icon(Icons.Rounded.Delete, null, Modifier.clickable { clear() })
                }
            }
        }
    }
}
