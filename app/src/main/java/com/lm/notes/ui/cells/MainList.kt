package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun MainList(notesList: List<NoteModel>, note: @Composable List<NoteModel>.(Int) -> Unit) {
    with(mainDep) {
       // val modifierSize = remember { Modifier.size(width, height - 80.dp) }
        with(notesViewModel.uiStates) {
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
                              //          modifier = modifierSize,
                                        contentAlignment = Alignment.TopCenter
                                    ) { note(notesList, it) }
                                } else note(notesList, it)
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .noRippleClickable(remember { { cancelDeleteMode() } }),
                    reverseLayout = getIsReversLayout,
                    contentPadding = PaddingValues(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                )
            }
        }
    }
}