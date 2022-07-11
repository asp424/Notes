package com.lm.notes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.NotesViewModel

@Composable
fun BottomBar() {
    with(mainDep) {
        LocalViewModelStoreOwner.current?.also { owner ->
            val notesViewModel = remember {
                ViewModelProvider(owner, viewModelFactory)[NotesViewModel::class.java]
            }
            val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(onClick = { notesViewModel.addNewNote(lifecycleScope) }) {
                    Text(text = "new")
                }
            }
        }
    }
}
