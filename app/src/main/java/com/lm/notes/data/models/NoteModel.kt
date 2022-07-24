package com.lm.notes.data.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange

data class NoteModel(
    val id: String = "",
    var text: String = "",
    var sizeX: Float = 0f,
    var sizeY: Float = 0f,
    var underlinedMap: MutableState<String> = mutableStateOf(""),
    var isSelected: MutableState<Boolean> = mutableStateOf(false),
    var selectedTextRange: TextRange = TextRange.Zero,
    var timestampChange: Long = 0,
    var textState: MutableState<String> = mutableStateOf(""),
    var sizeXState: MutableState<Float> = mutableStateOf(200f),
    var sizeYState: MutableState<Float> = mutableStateOf(60f),
    var timestampCreate: Long = 0,
    var timestampChangeState: MutableState<Long> = mutableStateOf(0),
    var isChanged: Boolean = false
)
