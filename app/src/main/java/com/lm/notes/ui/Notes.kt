package com.lm.notes.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Crop
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.ComposeDependencies
import javax.inject.Inject

interface Notes {

    @Composable
    fun Default(noteModel: NoteModel)

    class Base @Inject constructor(
        private val composeDependencies: ComposeDependencies
    ) : Notes {

        @Composable
        override fun Default(noteModel: NoteModel) {
            with(noteModel) {
                composeDependencies.mainScreenDepsLocal().apply {
                    LaunchedEffect(true) {
                        isChanged.value = false
                    }
                    Box(
                        modifier = Modifier
                            .background(
                                White,
                                RoundedCornerShape(60.dp)
                            )
                            .width(sizeXState.value)
                            .height(sizeYState.value)
                            .padding(bottom = 10.dp)
                            .fillMaxWidth()
                            .border(BorderStroke(1.dp, Color.Black))
                    ) {
                        TextField(
                            value = noteState.value,
                            onValueChange = { str ->
                                noteState.value = str
                                if (!isChanged.value) isChanged.value = true
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = White
                            ), modifier = Modifier
                                .fillMaxSize()
                                .border(BorderStroke(1.dp, Color.Black))
                        )
                        Icon(
                            Icons.Rounded.Crop, null,
                            modifier = Modifier
                                .offset(sizeXState.value - 30.dp, sizeYState.value - 40.dp)
                                .pointerInput(Unit) {
                                    detectDragGestures { change, dragAmount ->
                                        if (!isChanged.value) isChanged.value = true
                                        change.consume()
                                        if (sizeXState.value + dragAmount.x.dp in 200.dp..width - 40.dp
                                        ) {
                                            sizeXState.value += dragAmount.x.dp
                                        }
                                        if (sizeYState.value + dragAmount.y.dp in 60.dp..height - 60.dp
                                        ) {
                                            sizeYState.value += dragAmount.y.dp
                                        }
                                    }
                                }
                        )
                    }
                }
            }
        }
    }
}

