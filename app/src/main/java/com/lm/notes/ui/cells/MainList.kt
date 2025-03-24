package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun MainList(notesList: List<NoteModel>, note: @Composable List<NoteModel>.(Int) -> Unit) {
    with(mainDep) {
        with(notesViewModel.uiStates) {
            with(notesList) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .noRippleClickable(remember { { cancelDeleteMode() } }),
                         listState,
                    PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 120.dp),
                    getIsReversLayout, Arrangement.Top, CenterHorizontally){
                    items(size, { get(it).id }, { get(it) }, { note(notesList, it) })
                }
            }
        }
    }
}