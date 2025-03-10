package com.lm.notes.ui.cells

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
                val values = remember(notesList) {
                    listOf(
                        getListFields<String> { it { formatTimestamp(timestampChangeState.value) } },
                        getListFields<String> { it { preview } },
                        getListFields<String> {
                            it {
                                with(header) { getHeader(isNewHeader(this)) }
                            }
                        },
                        getListFields<String> { it { id } },
                        getListFields<String> { it { formatTimestamp(timestampCreate) }}
                    )
                }
                val indication = ripple(color = uiStates.getMainColor)
                val interactionSource = remember { MutableInteractionSource() }

                Note(
                    getListFields {
                        it {
                            with(uiStates) {
                                Modifier.setClickOnNote(
                                    notesViewModel, this@it, navController,
                                    interactionSource, indication, coroutine
                                )
                            }
                        }
                    }[i], values[0][i], values[1][i], values[2][i], values[3][i], values[4][i], i
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





