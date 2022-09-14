package com.lm.notes.data.local_data

import androidx.compose.ui.text.input.TextFieldValue
import com.lm.notes.data.models.NoteModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface NoteData {

    val noteModelFullScreen: StateFlow<NoteModel>

    fun setFullscreenNoteModel(noteModel: NoteModel)

    fun isMustRemoveFromList(): Boolean

    fun updateNoteFromUi(newText: String, actualTime: Long)

    fun isNewHeader(text: String): Boolean

    fun updateHeaderFromUi(text: TextFieldValue)
    class Base @Inject constructor() : NoteData {

        private val _noteModelFullScreen = MutableStateFlow(NoteModel())

        override val noteModelFullScreen = _noteModelFullScreen.asStateFlow()
        override fun setFullscreenNoteModel(noteModel: NoteModel) {
            _noteModelFullScreen.value = noteModel
        }

        override fun isMustRemoveFromList() = with(noteModelFullScreen.value) {
            (text.isEmpty() && isNewHeader(headerState.value.text) &&
                    timestampChangeState.value == timestampCreate)
        }

        override fun updateNoteFromUi(newText: String, actualTime: Long) =
            with(noteModelFullScreen.value) {
                if (text != newText) {
                    timestampChangeState.value = actualTime
                    text = newText
                    isChanged = true
                } else {
                    timestampChangeState.value = initTime
                    isChanged = false
                }
            }

        override fun isNewHeader(text: String) = text.startsWith(NEW_TAG)

        override fun updateHeaderFromUi(text: TextFieldValue) = with(noteModelFullScreen.value) {
            headerState.value = text
            if (text.text != this.text) isChanged = true
        }

        companion object{
            const val NEW_TAG = "^^^^$"
        }
    }
}