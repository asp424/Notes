package com.lm.notes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
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

            Box(Modifier.fillMaxSize()) {
                notesViewModel.notesList.collectAsState().value.apply {
                    find { it.id == notesViewModel.noteId }?.apply {

                        CustomTextField(this, 0.dp)
                    }
                }
            }
        }
    }
}
