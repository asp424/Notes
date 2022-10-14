package com.lm.notes.ui.cells

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.bars.header
import com.lm.notes.utils.formatTimestamp

@Composable
fun MainColumn() {
    with(mainDep) {
        with(notesViewModel) {
            with(editTextController) {
                uiStates.apply {
                    val notesList by notesList.collectAsState()
                    val times by remember {
                        derivedStateOf {
                            notesList.getListFields {
                                it.invoke { formatTimestamp(timestampChangeState.value) }
                            }
                        }
                    }

                    val headers by remember {
                        derivedStateOf {
                            notesList.getListFields {
                                it.invoke {
                                    with(headerState.value.text) {
                                        header(isNewHeader(this))
                                    }
                                }
                            }
                        }
                    }

                    val notesTexts by remember {
                        derivedStateOf {
                            notesList.getListFields {
                                it.invoke { fromHtml(text).toString() }
                            }
                        }
                    }

                    val idS by remember {
                        derivedStateOf {
                            notesList.getListFields { it.invoke { id } }
                        }
                    }

                    val cardModifier by remember {
                        derivedStateOf {
                            mutableListOf<Modifier>().apply {
                                notesList.forEach { noteModel ->
                                    with(noteModel) {
                                        add(Modifier.pointerInput(Unit) {
                                            detectTapGestures(onTap = {
                                                if (getIsDeleteMode) {
                                                    if (listDeleteAble.contains(id)) {
                                                        removeFromDeleteAbleList(id)
                                                        if (listDeleteAble.isEmpty()) {
                                                            cancelDeleteMode()
                                                        }
                                                    } else addToDeleteAbleList(id)
                                                }
                                                if (getIsClickableNote && !getIsDeleteMode) {
                                                    false.setIsClickableNote
                                                    setFullscreenNoteModel(id, text)
                                                    setText(text)
                                                    updateNoteFromUi(text)
                                                    clipboardProvider.clipBoardIsNotEmpty?.setClipboardIsEmpty
                                                    false.setIsSelected
                                                    navController.navigate("fullScreenNote") {
                                                        popUpTo("mainList")
                                                    }
                                                }
                                            },
                                                onLongPress = {
                                                    setDeleteMode()
                                                    addToDeleteAbleList(noteModel.id)
                                                })
                                        })
                                    }
                                }
                            }
                        }
                    }

                    MainList(notesList) {
                        Note(cardModifier[it], times[it], notesTexts[it], headers[it], idS[it])
                    }
                }
            }
        }
    }
}

fun <T> List<NoteModel>.getListFields(ass: ((NoteModel.() -> T) -> Unit) -> Unit) =
    mutableListOf<T>().apply {
        this@getListFields.forEach { m ->
            ass.invoke { add(it(m)) }
        }
    }





