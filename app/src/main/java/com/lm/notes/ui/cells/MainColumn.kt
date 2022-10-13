package com.lm.notes.ui.cells

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.bars.header
import com.lm.notes.utils.formatTimestamp
import com.lm.notes.utils.noRippleClickable

@Composable
fun MainColumn() {
    with(mainDep) {
        with(notesViewModel) {
            with(editTextController) {
                uiStates.apply {
                    val notesList by notesList.collectAsState()

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
                                        with(it.headerState.value.text) {
                                            header(isNewHeader(this))
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
                                    add(fromHtml(it.text).toString())
                                }
                            }
                        }
                    }

                    val idS by remember {
                        derivedStateOf {
                            mutableListOf<String>().apply {
                                notesList.forEach {
                                    add(it.id)
                                }
                            }
                        }
                    }

                    val cardModifier by remember {
                        derivedStateOf {
                            mutableListOf<Modifier>().apply {
                                    notesList.forEach { model ->
                                        add(
                                            Modifier
                                                .padding(bottom = 10.dp)
                                                .fillMaxWidth()
                                                .wrapContentHeight()
                                                .pointerInput(Unit) {
                                                    detectTapGestures(
                                                        onTap = {
                                                            if (getIsClickableNote) {
                                                                false.setIsClickableNote
                                                                if (!getIsDeleteMode) {
                                                                    setFullscreenNoteModel(
                                                                        model.id,
                                                                        model.text
                                                                    )
                                                                    setText(model.text)
                                                                    clipboardProvider.clipBoardIsNotEmpty?.setClipboardIsEmpty
                                                                    false.setIsSelected
                                                                    navController
                                                                        .navigate("fullScreenNote") {
                                                                            popUpTo("mainList")
                                                                        }
                                                                } else {
                                                                    if (listDeleteAble.contains(
                                                                            model.id
                                                                        )
                                                                    ) {
                                                                        removeFromDeleteAbleList(
                                                                            model.id
                                                                        )
                                                                        if (listDeleteAble.isEmpty()) {
                                                                            setMainMode()
                                                                        }
                                                                    } else addToDeleteAbleList(model.id)
                                                                }
                                                            }
                                                        },
                                                        onLongPress = {
                                                            setDeleteMode()
                                                            addToDeleteAbleList(model.id)
                                                        }
                                                    )
                                                }
                                        )
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
                                                Note(
                                                    cardModifier[it],
                                                    times[it],
                                                    notesTexts[it],
                                                    headers[it],
                                                    idS[it]
                                                )
                                            }
                                        } else Note(
                                            cardModifier[it],
                                            times[it],
                                            notesTexts[it],
                                            headers[it],
                                            idS[it]
                                        )
                                    }
                                )
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .noRippleClickable {
                                    setMainMode()
                                },
                            contentPadding = PaddingValues(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                    }
                }
            }
        }
    }
}

