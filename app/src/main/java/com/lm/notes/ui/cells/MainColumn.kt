package com.lm.notes.ui.cells

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.formatTimestamp
import com.lm.notes.utils.getHeader

@Composable
fun MainColumn() {
    with(mainDep) {
        with(notesViewModel) {
            val notesList by notesList.collectAsState()
            MainList(notesList) { i ->
                val values = remember {
                    listOf(
                        getListFields<String> { it { formatTimestamp(timestampChangeState.value) } },
                        getListFields { it { editTextController.fromHtml(text).toString() } },
                        getListFields {
                            it {
                                with(headerState.value.text) {
                                    getHeader(isNewHeader(this))
                                }
                            }
                        },
                        getListFields { it { id } },
                    )
                }
                Note(
                    getListFields {
                        it {
                            with(uiStates) {
                                Modifier.setClickOnNote(notesViewModel, this@it, navController)
                            }
                        }
                    }[i], values[0][i], values[1][i], values[2][i], values[3][i]
                )
            }
        }
    }
}

fun <T> List<NoteModel>.getListFields(value: ((NoteModel.() -> T) -> Unit) -> Unit) =
    derivedStateOf {
        mutableListOf<T>().apply {
            this@getListFields.forEach { m -> value { add(it(m)) } }
        }.toList()
    }.value





