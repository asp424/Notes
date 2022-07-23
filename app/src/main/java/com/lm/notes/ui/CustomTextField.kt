package com.lm.notes.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.utils.log

@Composable
fun CustomTextField(noteModel: NoteModel, padding: Dp = 28.dp) {
    with(noteModel) {
        val initTimeStampChange = remember { timestampChangeState.value }
        val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

        with(MainDep.mainDep) {
            LocalViewModelStoreOwner.current?.also { ownerVM ->
                val notesViewModel = remember {
                    ViewModelProvider(ownerVM, viewModelFactory)[NotesViewModel::class.java]
                }

                TextField(
                    value = textState.value,
                    onValueChange = { str ->
                        notesViewModel.updateTextAndDate(
                            noteModel, initTimeStampChange, str, lifecycleScope
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = Color.White
                    ), modifier = Modifier
                        .fillMaxSize()
                        .padding(padding), textStyle = TextStyle(
                    ), visualTransformation = textTranslator(transformMap)
                )
            }
        }
    }
}

fun textTranslator(transformMap: String) = VisualTransformation { text ->
    val trimmed = if (text.text.isNotEmpty())
        text.text.substring(0 until text.text.length) else text.text
    val builder = AnnotatedString.Builder().apply {
        for (i in trimmed.indices) {
            if (i != text.text.length) {
                if (i in underlinedChars(transformMap)) {
                    withStyle(style = SpanStyle(textDecoration = Underline)) {
                        append("${trimmed[i]}")
                    }
                } else append("${trimmed[i]}")

            }
        }
    }
    TransformedText(builder.toAnnotatedString(), OffsetMapping.Identity)
}

private fun underlinedChars(transformMap: String) = with(transformMap) {
        if (checkForEmpty) {
            IntRange(
                substringAfter("start").substringBefore("u").toInt(),
                substringAfter("u").substringBefore("end").toInt()
            )
        } else IntRange.EMPTY
    }

private val String.checkForEmpty get()
= substringAfter("start").substringBefore("u") != "E"






