package com.lm.notes.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextField(noteModel: NoteModel, padding: Dp = 28.dp) {
    val focusRequest = LocalFocusManager.current

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
                        selectedTextRange.value = str.selection
                        str.selection.log
                        if (str.selection.length != 0) {
                            if (!isSelected.value)
                                coroutine.launch(IO) {
                                    delay(400)
                                    isSelected.value = true

                                }
                        } else {
                            if (isSelected.value)
                                coroutine.launch(IO) {
                                    delay(400)
                                    isSelected.value = false
                                }
                        }
                        textFieldText = str
                        notesViewModel.updateData(
                            noteModel, initTimeStampChange, str.text
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = Color.White
                    ),
                    placeholder = {
                        if (padding == 0.dp) Text(
                            text = "Note...",
                            color = Color.LightGray
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .border(BorderStroke(1.dp, Black))
                        .padding(padding),
                    textStyle = TextStyle(
                    ),
                    visualTransformation = textTranslator(
                        formatList(boldMap.value, ':'),
                        formatList(underlinedMap.value, '@')
                    ),
                )
            }
        }
    }
}

fun textTranslator(boldsList: List<Int>, underlinedList: List<Int>) = VisualTransformation { text ->
    val builder = AnnotatedString.Builder().apply {
        for (i in text.text.indices) {
            withStyle(
                style = SpanStyle(
                    fontWeight = if (boldsList.contains(i)) FontWeight.Bold else FontWeight.Normal,
                    textDecoration = if (underlinedList.contains(i)) TextDecoration.Underline
                    else TextDecoration.None
                )
            ) {
                append("${text.text[i]}")
            }
        }
    }
    TransformedText(builder.toAnnotatedString(), OffsetMapping.Identity)
}

fun formatList(boldMap: String, delimiter: Char) = boldMap.split(delimiter).map {
    if (it.isNotEmpty()) it.toInt() else -1
}.filter { it != -1 }

fun isHaveSelected(
    selectedTextRange: TextRange,
    map: List<Int>
) = with(selectedTextRange) {
    map.any { (start until end).toList().contains(it) }
}

fun deselectAll(selectedTextRange: TextRange, map: MutableState<String>, delimiter: String) =
    with(selectedTextRange) {
        (start until end).forEach {
            map.value = map.value.replace("$delimiter$it$delimiter", "")
        }
    }










