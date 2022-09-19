package com.lm.notes.ui.cells

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.addTextChangedListener
import com.lm.notes.di.compose.MainDep.mainDep

@Composable
fun EditText() {
    mainDep.apply {
        spansProvider.apply {

            val listener = remember {
                editText.run {
                    addTextChangedListener {
                        notesViewModel.updateNoteFromUi(toHtml(text))
                        notesViewModel.noteModelFullScreen.value.textState.value = text.toString()
                    }
                }
            }

            Box(
                Modifier
                    .border(1.dp, Black)
                    .fillMaxSize(), Alignment.TopStart) {
                AndroidView({ editText },
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                )

                DisposableEffect(true) {
                    onDispose { editText.removeTextChangedListener(listener) }
                }
            }
        }
    }
}

