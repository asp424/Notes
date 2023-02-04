package com.lm.notes.data.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import com.google.firebase.database.PropertyName

@Immutable
data class NoteModel(
    @PropertyName("id")
    val id: String = "",
    @PropertyName("text")
    var text: String = "",
    @PropertyName("timestampChange")
    var timestampChange: Long = 0,
    @PropertyName("timestampCreate")
    var timestampCreate: Long = 0,
    @PropertyName("header")
    var header: String = "",
    var initTime: Long = 0,
    @PropertyName("preview")
    var preview: String = "",
    var timestampChangeState: MutableState<Long> = mutableStateOf(0),
    var isChanged: Boolean = false
)
