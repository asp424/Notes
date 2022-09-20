package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.bars.header
import com.lm.notes.utils.formatTimestamp
import com.lm.notes.utils.noRippleClickable

@Composable
fun MainColumn() {
    with(mainDep) {

        val notesList by notesViewModel.notesList.collectAsState()

        val times by remember {
            derivedStateOf {
                mutableListOf<String>().apply {
                    notesList.forEach {
                        add(formatTimestamp(it.timestampChangeState.value))
                    }
                }
            }
        }

        val headers by remember {
            derivedStateOf {
                mutableListOf<String>().apply {
                    notesList.forEach {
                        add(
                            with(it.headerState.value.text){
                                header(notesViewModel.isNewHeader(this))
                            }
                        )
                    }
                }
            }
        }

        val notesTexts by remember {
            derivedStateOf {
                mutableListOf<String>().apply {
                    notesList.forEach {
                        add(spansProvider.fromHtml(it.text).toString())
                    }
                }
            }
        }

        val cardModifier by remember {
            derivedStateOf {
                mutableListOf<Modifier>().apply {
                    notesList.forEach {
                        add(
                            Modifier
                                .padding(bottom = 10.dp)
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .noRippleClickable {
                                    notesViewModel.setFullscreenNoteModel(it.id, it.text)
                                    editTextProvider.setText(it.text)
                                    navController.navigate("fullScreenNote") {
                                        popUpTo("mainList")
                                    }
                                })
                    }
                }
            }
        }
        val modifierSize = remember { Modifier.size(width, height - 80.dp) }
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
                                    modifier = modifierSize,
                                    contentAlignment = Alignment.TopCenter
                                ) {
                                    Note(cardModifier[it], times[it], notesTexts[it], headers[it])
                                }
                            } else Note(cardModifier[it], times[it], notesTexts[it], headers[it])
                        }
                    )
                },
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )

            /* with(notesViewModel.paginatedList) {
                 LazyColumn(
                     modifier = Modifier
                         .fillMaxSize(),
                     contentPadding = PaddingValues(20.dp),
                     horizontalAlignment = Alignment.CenterHorizontally
                 ) {
                     items(key = { items[it].id }, count = items.size) { i ->

                         val item = items[i]
                         if (i >= items.size - 1
                             && !endReached
                             && !isLoading
                         ) notesViewModel.loadNextItems()
                          Note(item)
                     }
                     item {
                         if (isLoading) {
                             Row(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .padding(8.dp),
                                 horizontalArrangement = Arrangement.Center
                             ) {
                                 CircularProgressIndicator()
                             }
                         }
                     }
                 }

             */
        }
    }
}


