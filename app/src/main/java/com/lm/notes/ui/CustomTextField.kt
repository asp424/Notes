package com.lm.notes.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.utils.log
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CustomTextField(noteModel: NoteModel, padding: Dp = 28.dp) {
    with(noteModel) {
        val initTimeStampChange = remember { timestampChangeState.value }

        with(MainDep.mainDep) {
            LocalViewModelStoreOwner.current?.also { ownerVM ->

                val notesViewModel = remember {
                    ViewModelProvider(ownerVM, viewModelFactory)[NotesViewModel::class.java]
                }

                var textFieldText by remember {
                    mutableStateOf(TextFieldValue(textState.value))
                }
                TextField(
                    value = textFieldText,
                    onValueChange = { str ->
                        selectedTextRange = str.selection
                        if (str.selection.length != 0) {
                            if (!isSelected.value)
                                coroutine.launch(IO) {
                                    delay(400)
                                    isSelected.value = true
                                }
                        } else if (isSelected.value)
                            coroutine.launch(IO) {
                                delay(400)
                                isSelected.value = false
                            }
                        textFieldText = str
                        notesViewModel.updateData(
                            noteModel, initTimeStampChange, str.text, coroutine
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .border(BorderStroke(1.dp, Black))
                        .padding(padding),
                    textStyle = TextStyle(
                    ),
                    visualTransformation = textTranslator(underlinedMap.value),
                )
            }
        }
    }
}

fun textTranslator(underlinedMap: String) = VisualTransformation { text ->
    val trimmed = if (text.text.isNotEmpty())
        text.text.substring(0 until text.text.length) else text.text
    val builder = AnnotatedString.Builder().apply {
        for (i in trimmed.indices) {
            if (i != text.text.length) {
                if (i in underlinedChars(underlinedMap)) {
                    withStyle(style = SpanStyle(textDecoration = Underline)) {
                        append("${trimmed[i]}")
                    }
                } else append("${trimmed[i]}")
            }
        }
    }
    TransformedText(builder.toAnnotatedString(), OffsetMapping.Identity)
}

fun underlinedChars(underlinedMap: String) = with(underlinedMap) {
    if (checkForEmpty) TextRange(startUValue.toInt(), endUValue.toInt())
    else TextRange.Zero
}

val String.startUValue get() = substringAfter("s").substringBefore("u")

val String.endUValue get() = substringAfter("u").substringBefore("e")

private val String.checkForEmpty get() = startUValue != "E"

fun checkForIntersects(selectedTextRange: TextRange, underlinedMap: String) =
    selectedTextRange.intersects(underlinedChars(underlinedMap))

val String.chunkCount get() = filter { it == 's' }.length







