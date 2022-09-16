package com.lm.notes.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lm.notes.data.models.NoteModel
import com.lm.notes.ui.cells.EditText
import com.lm.notes.ui.cells.HeaderTextField

@Composable
fun FullScreenNote(
    noteModel: NoteModel
) {
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            HeaderTextField(noteModel)
            EditText()
        }
    }
}
