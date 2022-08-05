package com.lm.notes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep

@Composable
fun MainColumn(omMainColumn: (Boolean) -> Unit) {
    with(mainDep) {
        LaunchedEffect(true) {
            omMainColumn(true)
        }
        val notesList by notesViewModel.notesList.collectAsState()

        with(notesList) {
            LazyColumn(
                state = listState,
                content = {
                    items(
                        count = size,
                        key = { get(it).id },
                        itemContent = {
                            if (it == lastIndex) {
                                Box(
                                    modifier = Modifier.size(width, height - 80.dp),
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
