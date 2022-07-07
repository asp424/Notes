package com.lm.notes.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Crop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.lm.notes.di.compose.ComposeDependencies
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.presentation.ViewModels
import javax.inject.Inject


interface StateReceiver {

    @Composable
    fun Default()

    class Base @Inject constructor(
        private val viewModels: ViewModels,
        private val composeDependencies: ComposeDependencies
    ) : StateReceiver {

        @Composable
        override fun Default() {
            LocalViewModelStoreOwner.current?.also { owner ->
                val notesViewModel = remember {
                    viewModels.viewModelProvider(owner)[NotesViewModel::class.java]
                }
                composeDependencies.mainScreenDepsLocal().apply {

                    when (val notesList = notesViewModel.notesListState) {
                        is UiStates.Loading ->
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
                            { CircularProgressIndicator() }

                        is UiStates.Success -> {
                            val listState = rememberLazyListState()
                            val changedList = remember { mutableListOf<String>() }
                            LazyColumn(
                                content = {
                                    items(
                                        items = notesList.listNotes,
                                        key = { it.id }
                                    ) { noteModel ->
                                        noteModel.apply {
                                            Box(
                                                modifier = Modifier
                                                    .background(
                                                        Color.White,
                                                        RoundedCornerShape(60.dp)
                                                    )
                                                    .width(sizeX.value)
                                                    .height(sizeY.value)
                                                    .padding(bottom = 10.dp)
                                                    .fillMaxWidth()
                                                    .border(BorderStroke(1.dp, Color.Black))
                                            ) {
                                                TextField(
                                                    value = noteState.value,
                                                    onValueChange = { str ->
                                                        noteState.value = str
                                                        changedList.add(id)
                                                    },
                                                    colors = TextFieldDefaults.textFieldColors(
                                                        backgroundColor = Color.White
                                                    ), modifier = Modifier
                                                        .fillMaxSize()
                                                        .border(BorderStroke(1.dp, Color.Black))

                                                )
                                                Icon(
                                                    Icons.Rounded.Crop, null,
                                                    modifier = Modifier
                                                        .offset(
                                                            sizeX.value - 30.dp,
                                                            sizeY.value - 40.dp
                                                        )
                                                        .pointerInput(Unit) {
                                                            detectDragGestures { change, dragAmount ->
                                                                change.consume()
                                                                if (sizeX.value + dragAmount.x.dp in 200.dp..width - 40.dp
                                                                ) {
                                                                    sizeX.value += dragAmount.x.dp
                                                                }
                                                                if (sizeY.value + dragAmount.y.dp in 60.dp..height - 60.dp
                                                                ) {
                                                                    sizeY.value += dragAmount.y.dp
                                                                }
                                                            }
                                                        }
                                                )
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                state = listState
                            )

                            DisposableEffect(true) {
                                onDispose { notesViewModel.updateNote(changedList) }
                            }
                        }

                        is UiStates.Failure -> Text(text = notesList.message)
                        is UiStates.Update -> Unit
                    }
                }
            }
        }
    }
}