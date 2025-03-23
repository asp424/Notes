package com.lm.notes.ui.cells

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.lm.notes.di.compose.MainDep.mainDep


@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun EditTextAndroidView() {
    with(mainDep.notesViewModel) {
        with(editTextController) {
            with(uiStates) {
                LocalDensity.current.apply {
                    val scrollState = rememberScrollState()
                    Box(
                        Modifier
                            .border(2.dp, getMainColor)
                            .background(Color.White)
                            .fillMaxSize()
                            .alpha(0.8f),
                        Alignment.TopStart
                    ) {

                        AndroidView(
                            {
                                editText.parent?.apply {
                                    (editText.parent as ViewGroup).removeView(editText)
                                }
                                editText.apply { textSize = 16f }
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                            // .horizontalScroll(rememberScrollState())
                        )
                        Box(
                            Modifier
                                .offset(0.dp, -scrollState.value.toDp())
                        ) {
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
