package com.lm.notes.ui.cells

import androidx.compose.runtime.Composable
import com.lm.notes.presentation.NotesViewModel

@Composable
fun NotesViewModel.EditText() {
    KeyBoardListener()
    EditTextSetTextListener()
    EditTextAndroidView()
}

