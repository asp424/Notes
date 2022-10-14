package com.lm.notes.ui.cells

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.core.text.toHtml
import androidx.core.widget.addTextChangedListener
import com.lm.notes.di.compose.MainDep.mainDep

@Composable
fun EditTextSetTextListener() {
    with(mainDep.notesViewModel) {
        with(editTextController.editText) {
            val textListener = remember {
                run {
                    addTextChangedListener {
                        updateNoteFromUi(text.toHtml())
                        with(uiStates) { checkForEmptyText().setTextIsEmpty }
                    }
                }
            }
            DisposableEffect(true) {
                onDispose { removeTextChangedListener(textListener) }
            }
        }
    }
}