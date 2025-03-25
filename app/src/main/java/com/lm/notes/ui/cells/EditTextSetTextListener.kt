package com.lm.notes.ui.cells

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.core.text.toSpanned
import androidx.core.widget.addTextChangedListener
import com.lm.notes.presentation.NotesViewModel

@Composable
fun NotesViewModel.EditTextSetTextListener() {
    with(editTextController.editText) {
        val textListener = remember {
            run {
                addTextChangedListener {
                    if (!uiStates.getTranslateEnable) updateNoteFromUi(text.toSpanned())
                    editTextController.setLinesCount()
                }
            }
        }
        DisposableEffect(true) {
            onDispose { removeTextChangedListener(textListener) }
        }
    }
}
