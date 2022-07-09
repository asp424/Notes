package com.lm.notes.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.lm.notes.data.remote_data.firebase.NotesMapper
import com.lm.notes.di.compose.ComposeDependencies
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.presentation.ViewModels
import javax.inject.Inject


interface MainColumn {

    @Composable
    fun Default()

    class Base @Inject constructor(
        private val viewModels: ViewModels,
        private val composeDependencies: ComposeDependencies,
        private val notes: Notes,
        private val notesMapper: NotesMapper
    ) : MainColumn {

        @Composable
        override fun Default() {
            LocalViewModelStoreOwner.current?.also { owner ->
                val notesViewModel = remember {
                    viewModels.viewModelProvider(owner)[NotesViewModel::class.java]
                }
                composeDependencies.mainScreenDepsLocal().apply {
                    val notesList by notesViewModel.notesListState.collectAsState()
                    LazyColumn(
                        content = {
                            items(
                                count = notesList.size,
                                key = { notesList[it].id },
                                itemContent = {
                                    notes.Default(noteModel = notesList[it])
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
}
