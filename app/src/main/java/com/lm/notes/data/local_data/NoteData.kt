package com.lm.notes.data.local_data

import android.text.Spanned
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.text.toHtml
import com.lm.notes.data.models.NoteModel
import com.lm.notes.data.models.UiStates
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface NoteData {

    val noteModelFullScreen: StateFlow<NoteModel>

    fun setFullscreenNoteModel(noteModel: NoteModel)

    fun isMustRemoveFromList(): Boolean

    fun updateNoteFromUi(newText: Spanned, actualTime: Long)

    fun isNewHeader(text: String): Boolean

    fun updateHeaderFromUi(text: TextFieldValue)

    fun checkForEmptyText()

    class Base @Inject constructor(
        private val uiStates: UiStates,
        private val coroutineDispatcher: CoroutineDispatcher
    ) : NoteData {

        private val _noteModelFullScreen = MutableStateFlow(NoteModel())

        override val noteModelFullScreen = _noteModelFullScreen.asStateFlow()

        override fun setFullscreenNoteModel(noteModel: NoteModel) {
            _noteModelFullScreen.value = noteModel
        }

        override fun isMustRemoveFromList() = with(noteModelFullScreen.value) {
            (text.isEmpty()
                    && isNewHeader(header)// && timestampChangeState.value == timestampCreate
            )
        }

        override fun updateNoteFromUi(newText: Spanned, actualTime: Long) {
            with(noteModelFullScreen.value) {
                newText.toHtml().also { htmlText ->
                    if (text != htmlText) {
                        timestampChangeState.value = actualTime
                        text = htmlText
                        preview = if (newText.length >= 40) "${newText.substring(0, 40)}..."
                        else newText.toString()
                        isChanged = true
                    } else {
                        timestampChangeState.value = initTime
                        isChanged = false
                    }
                    checkForEmptyText()
                }
            }
        }

        override fun isNewHeader(text: String) = text.startsWith(NEW_TAG)

        override fun updateHeaderFromUi(text: TextFieldValue) = with(noteModelFullScreen.value) {
            header = text.text
            isChanged = true
        }

        override fun checkForEmptyText() {
            with(uiStates) {
                CoroutineScope(coroutineDispatcher).launch {
                    noteModelFullScreen.value.text.isNotEmpty().setTextIsEmpty
                }
            }
        }

        companion object {
            const val NEW_TAG = "^^^^$"
        }
    }
}