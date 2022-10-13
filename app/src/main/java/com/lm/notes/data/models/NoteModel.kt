package com.lm.notes.data.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue

@Immutable
data class NoteModel(
    val id: String = "",
    var text: String = "",
    var timestampChange: Long = 0,
    var timestampCreate: Long = 0,
    var header: String = "",
    var initTime: Long = 0,
    var headerState: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
    var timestampChangeState: MutableState<Long> = mutableStateOf(0),
    var isChanged: Boolean = false
)
