package com.lm.notes.utils.format_text

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration.Companion.None
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.text.withStyle
import com.lm.notes.data.local_data.NoteData
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.ui.theme.green
import javax.inject.Inject


interface TextFormatter {

    fun translate(map: MutableState<String>, tag: Char)

    fun getColor(map: String, tag: Char): Color

    fun transformText(boldsList: List<Int>, underlinedList: List<Int>, italicList: List<Int>)
            : VisualTransformation

    fun formattedIndicesList(map: String, tag: Char): List<Int>

    fun reformatSelected(map: MutableState<String>, tag: Char)

    fun isHaveFormattedSymbol(map: List<Int>): Boolean

    class Base @Inject constructor(
        private val noteData: NoteData
    ) : TextFormatter {

        override fun translate(map: MutableState<String>, tag: Char) {
            if (isHaveFormattedSymbol(formattedIndicesList(map.value, tag))
            ) reformatSelected(map, tag)
            else noteModel.textState.value.selection.selectedIndices.forEach {
                map.value = map.value + "$tag$it$tag"
            }
        }

        override fun getColor(map: String, tag: Char) =
            if (isHaveFormattedSymbol(formattedIndicesList(map, tag)))
                green else White

        override fun transformText(
            boldsList: List<Int>,
            underlinedList: List<Int>,
            italicList: List<Int>
        ) = VisualTransformation { text ->
            val builder = AnnotatedString.Builder().apply {
                text.text.indices.forEach {
                    withStyle(
                        SpanStyle(
                            fontWeight = if (it in boldsList) Bold else Normal,
                            textDecoration = if (it in underlinedList) Underline else None,
                            fontStyle = if (it in italicList) Italic else FontStyle.Normal
                        )
                    ) { append("${text.text[it]}") }
                }
            }
            TransformedText(builder.toAnnotatedString(), OffsetMapping.Identity)
        }

        override fun formattedIndicesList(map: String, tag: Char) = map.split(tag)
            .map { if (it.isNotEmpty()) it.toInt() else -1 }.filter { it != -1 }

        override fun isHaveFormattedSymbol(map: List<Int>) =
            with(noteModel.textState.value.selection.selectedIndices.toList())
            { map.any { it in this } }

        override fun reformatSelected(
            map: MutableState<String>, tag: Char
        ) = with(noteModel.textState.value.selection.selectedIndices) {
            forEach { map.value = map.value.replace("$tag$it$tag", "") }
        }

        private val TextRange.selectedIndices get() = (start until end)

        private val noteModel get() = noteData.noteModelFullScreen.value

        companion object {
            const val UNDERLINE = '@'
            const val BOLD = ':'
            const val ITALIC = '$'
        }
    }
}