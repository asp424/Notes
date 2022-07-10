package com.lm.notes.data.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class NoteModel(
    val id: String = "",
    val timestamp: Long = 0,
    var text: String = "",
    var sizeX: String = "",
    var sizeY: String = "",
    var noteState: MutableState<String> = mutableStateOf(""),
    var sizeXState: MutableState<Dp> = mutableStateOf(200.dp),
    var sizeYState: MutableState<Dp> = mutableStateOf(60.dp),
    var isChanged: MutableState<Boolean> = mutableStateOf(false)
)
