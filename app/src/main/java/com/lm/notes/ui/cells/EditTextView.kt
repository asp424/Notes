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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.lm.notes.di.compose.MainDep.mainDep


@Composable
fun EditTextAndroidView() {
    var scale by remember { mutableStateOf(1f) }
    val state = rememberTransformableState { zoomChange, _, _ -> scale *= zoomChange }

    with(mainDep.notesViewModel) {
        with(editTextController) {
            with(uiStates) {
                LocalDensity.current.apply {
                    Box(
                        Modifier
                            .border(1.dp, getMainColor)
                            .transformable(state)
                            .fillMaxSize()
                            .graphicsLayer { editText.textSize = scale * 16 }, Alignment.TopStart
                    ) {
                        AndroidView(
                            { editText },
                            Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .alpha(0.8f)
                        )
                    }
                }
            }
        }
    }
}