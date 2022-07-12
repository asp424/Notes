package com.lm.notes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.NotesViewModel

@Composable
fun MainColumn() {
    with(mainDep) {
        LocalViewModelStoreOwner.current?.also { ownerVM ->
            val notesViewModel = remember {
                ViewModelProvider(ownerVM, viewModelFactory)[NotesViewModel::class.java]
            }
            notesViewModel.notesList.collectAsState().value.apply {
                LazyColumn(
                    content = {
                        items(
                            count = size,
                            key = { get(it).id },
                            itemContent = {
                                    if (it == lastIndex) {
                                        Box(modifier = Modifier.size(width, height - 80.dp),
                                        contentAlignment = Alignment.TopCenter
                                            ) {
                                            Note(get(it))
                                        }
                                } else Note(get(it))

                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
            }
        }
    }
}
