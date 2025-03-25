package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.Arrangement.Top
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.utils.formatTimestamp
import com.lm.notes.utils.getHeader
import com.lm.notes.utils.modifiers.noRippleClickable
import com.lm.notes.utils.modifiers.noteClickable

@Composable
fun NotesViewModel.NotesList() = with(uiStates) {
    val notesList by notesList.collectAsState()
    LazyColumn(
        Modifier
            .fillMaxSize()
            .noRippleClickable(remember { { cancelDeleteMode() } }),
        rememberLazyListState(),
        PaddingValues(20.dp, 20.dp, 20.dp, 120.dp), getIsReversLayout, Top, CenterHorizontally
    ) {
        with(notesList) {
            items(size, { get(it).id }, { get(it) }, { i ->
                notesList[i].apply {
                    Note(
                        Modifier.noteClickable(this),
                        formatTimestamp(timestampChangeState.value), preview,
                        with(header) { getHeader(isNewHeader(this)) }, id,
                        formatTimestamp(timestampCreate)
                    )
                }
            })
        }
    }
}








