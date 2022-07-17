package com.lm.notes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.lm.notes.di.compose.MainDep
import com.lm.notes.presentation.NotesViewModel
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
            val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

            Box(Modifier.fillMaxSize()) {
                notesViewModel.notesList.collectAsState().value.apply {
                    find { it.id == notesViewModel.noteId }?.apply {

                        val initTimeStampChange = remember { timestampChangeState.value }

                        TextField(
                            value = textState.value,
                            onValueChange = { str ->
                                notesViewModel.updateTextAndDate(
                                    this,
                                    initTimeStampChange,
                                    str,
                                    lifecycleScope
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White
                            ), modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
