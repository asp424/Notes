package com.lm.notes.ui.cells

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.core.text.toSpanned
import androidx.core.widget.addTextChangedListener
import com.lm.notes.di.compose.MainDep.mainDep

@Composable
fun EditTextSetTextListener() {
    with(mainDep) {
        with(notesViewModel) {
            with(editTextController) {
                with(editText) {
                    with(uiStates) {
                        val textListener = remember {
                            run {
                                addTextChangedListener {
                                    if (!getTranslateEnable) updateNoteFromUi(text.toSpanned())
                                    setLinesCount()
                                }
                            }
                        }
                        DisposableEffect(true) {
                            onDispose { removeTextChangedListener(textListener) }
                        }
                    }
                }
            }
        }
    }
}