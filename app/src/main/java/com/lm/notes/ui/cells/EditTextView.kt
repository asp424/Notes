package com.lm.notes.ui.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.view.LoadStatesEditText

@Composable
fun EditTextAndroidView() {
    var scale by remember { mutableStateOf(1f) }
    val state = rememberTransformableState { zoomChange, _, _ -> //scale *= zoomChange
    }
    with(mainDep.notesViewModel) {
        with(editTextController) {
            with(uiStates) {
                LocalDensity.current.apply {
                    val scrollState = rememberScrollState()
                    Box(
                        Modifier
                            .border(2.dp, getMainColor)
                            .transformable(state)
                            .background(Color.White)
                            .alpha(0.8f)
                            .graphicsLayer { editText.textSize = scale * 16 },
                        Alignment.TopStart
                    ) {

                        AndroidView(
                            { editText },
                            Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                                .horizontalScroll(rememberScrollState())
                        )
                        Box(
                            Modifier
                                .offset(0.dp, -scrollState.value.toDp())) {
                            repeat(getLinesCounter) {
                                Text(
                                    text = it.toString(),
                                    fontSize = editText.textSize.toSp(),
                                    modifier = Modifier
                                        .offset(0.dp, (it * 19).dp)
                                        .padding(top = 16.dp, start = 10.dp),
                                    color = Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
