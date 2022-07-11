package com.lm.notes.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Crop
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.utils.noRippleClickable

@Composable
fun Note(noteModel: NoteModel) {
    with(noteModel) {
        with(mainDep) {
            LocalViewModelStoreOwner.current?.also { ownerVM ->
                val notesViewModel = remember {
                    ViewModelProvider(ownerVM, viewModelFactory)[NotesViewModel::class.java]
                }

                val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

                LaunchedEffect(true) { isChanged.value = false }
                Box(
                    modifier = Modifier
                        .width(sizeXState.value.dp)
                        .height(sizeYState.value.dp)
                        .padding(bottom = 10.dp)
                ) {
                    TextField(
                        value = noteState.value,
                        onValueChange = { str ->
                            noteState.value = str
                            if (!isChanged.value) isChanged.value = true
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = White
                        ), modifier = Modifier
                            .fillMaxSize()
                    )

                    Icon(
                        Icons.Rounded.Crop, null,
                        modifier = Modifier
                            .offset(sizeXState.value.dp - 30.dp, sizeYState.value.dp - 40.dp)
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    if (!isChanged.value) isChanged.value = true
                                    change.consume()
                                    if (sizeXState.value.dp + dragAmount.x.dp in 200.dp..width - 40.dp
                                    ) sizeXState.value += dragAmount.x

                                    if (sizeYState.value.dp + dragAmount.y.dp in 75.dp..height - 60.dp
                                    ) sizeYState.value += dragAmount.y
                                }
                            }
                    )

                    Icon(
                        Icons.Rounded.Delete, null, modifier = Modifier
                            .offset(sizeXState.value.dp - 30.dp, 5.dp)
                            .noRippleClickable {
                                notesViewModel.deleteNoteById(lifecycleScope, id)
                            }
                    )
                }
            }
        }
    }
}





