package com.lm.notes.data.remote_data.firebase

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class NoteModel(
    val id: String = "",
    val timestamp: Long = 0,
    var note: String = "",
    var noteState: MutableState<String> = mutableStateOf(""),
    var sizeX: MutableState<Dp> = mutableStateOf(200.dp),
    var sizeY: MutableState<Dp> = mutableStateOf(60.dp),
    var isChanged: MutableState<Boolean> = mutableStateOf(false)
)
