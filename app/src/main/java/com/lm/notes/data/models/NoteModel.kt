package com.lm.notes.data.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class NoteModel(
    val id: String = "",
    val timestamp: Long = 0,
    var text: String = "",
    var sizeX: Float = 0f,
    var sizeY: Float = 0f,
    var noteState: MutableState<String> = mutableStateOf(""),
    var sizeXState: MutableState<Float> = mutableStateOf(200f),
    var sizeYState: MutableState<Float> = mutableStateOf(60f),
    var isChanged: MutableState<Boolean> = mutableStateOf(false)
)
