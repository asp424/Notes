package com.lm.notes.data.local_data

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import com.lm.notes.data.models.NoteModel
import com.lm.notes.utils.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface NoteData {

    val noteModelFullScreen: StateFlow<NoteModel>

    fun setFullscreenNoteModel(noteModel: NoteModel)

    fun updateFromUi(newText: TextFieldValue, actualTime: Long): Job

    class Base @Inject constructor() : NoteData {

        private val _noteModelFullScreen = MutableStateFlow(NoteModel())

        override val noteModelFullScreen = _noteModelFullScreen.asStateFlow()

        override fun setFullscreenNoteModel(noteModel: NoteModel) {
            _noteModelFullScreen.value = noteModel
        }

        override fun updateFromUi(newText: TextFieldValue, actualTime: Long) =
            with(noteModelFullScreen.value) {
                CoroutineScope(IO).launch {
                    "aa".log
                    textState.value = newText
                    if (text != textState.value.text) {
                        timestampChangeState.value = actualTime
                        isChanged = true
                    } else {
                        timestampChangeState.value = initTime
                        isChanged = false
                    }
                }
            }
    }
}