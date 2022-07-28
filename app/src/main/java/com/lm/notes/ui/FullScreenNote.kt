package com.lm.notes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.lm.notes.di.compose.MainDep
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.ui.theme.gray
import kotlinx.coroutines.delay

@Composable
fun FullScreenNote(onFullScreenNote: (Boolean) -> Unit) {
    with(MainDep.mainDep) {
        LaunchedEffect(true) {
            delay(200)
            onFullScreenNote(false)
        }
        LocalViewModelStoreOwner.current?.also { ownerVM ->
            val notesViewModel = remember {
                ViewModelProvider(ownerVM, viewModelFactory)[NotesViewModel::class.java]
            }
            notesViewModel.notesList.value.find { it.id == notesViewModel.noteId }?.apply {

                Box(Modifier.fillMaxSize()) {
                    Column() {
                        TextField(value = headerState.value,
                            onValueChange = {
                                headerState.value = it
                                if (!isChanged) isChanged = true
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                backgroundColor = gray
                            ),
                            modifier = Modifier.width(width),
                            maxLines = 1,
                            textStyle = TextStyle(
                                fontWeight = FontWeight.Bold, fontSize = 18.sp
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                            placeholder = { Text(text = "Header", color = Color.LightGray) }
                        )
                        CustomTextField(this@apply, 0.dp)
                    }
                }
            }
        }
    }
}
