package com.lm.notes.ui.cells

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.addTextChangedListener
import com.lm.notes.di.compose.MainDep.mainDep

@Composable
fun EditText() {
    mainDep.apply {
        notesViewModel.spansProvider.apply {
            var scale by remember { mutableStateOf(1f) }
            val state = rememberTransformableState { zoomChange, _, _ ->
                scale *= zoomChange
                // if (zoomChange != 1f) setRelativeSpan(zoomChange)
            }

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
                    .transformable(state)
                    .fillMaxSize(), Alignment.TopStart
            ) {
                AndroidView(
                    { editText },
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .alpha(0.8f).graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                )

                DisposableEffect(true) {
                    onDispose { editText.removeTextChangedListener(listener) }
                }
            }
        }
    }
}

