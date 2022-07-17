package com.lm.notes.ui

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.lm.notes.R
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.MainActivity
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.ui.theme.bar
import com.lm.notes.utils.formatTimestamp
import com.lm.notes.utils.log
import com.lm.notes.utils.longToast
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

                var isFullScreen by remember { mutableStateOf(false) }

                val initTimeStampChange = remember { timestampChangeState.value }

                val fullScreenSize by animateOffsetAsState(
                    if (isFullScreen) Offset(
                        width.value - sizeXState.value, height.value -
                                sizeYState.value
                    ) else Offset.Zero
                )

                LocalDensity.current.apply {
                    Box(
                        modifier = Modifier
                            .width(sizeXState.value.dp + fullScreenSize.x.dp)
                            .height(sizeYState.value.dp + fullScreenSize.y.dp)
                            .padding(bottom = 10.dp)
                            .background(bar, RoundedCornerShape(10.dp))
                    ) {
                        TextField(
                            value = textState.value,
                            onValueChange = { str ->
                                notesViewModel.updateTextAndDate(
                                    noteModel, initTimeStampChange, str, lifecycleScope
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = White
                            ), modifier = Modifier
                                .fillMaxSize()
                                .padding(28.dp)
                        )
                        Text(
                            text = formatTimestamp(timestampChangeState.value),
                            fontStyle = FontStyle.Italic, fontSize = 11.sp,
                            modifier = Modifier
                                .padding(2.dp)
                                .offset(0.dp, sizeYState.value.dp - 30.dp),
                            style = TextStyle(fontWeight = FontWeight.Bold), color = White
                        )
                        Image(
                            painter = painterResource(id = R.drawable.hand), null,
                            modifier = Modifier
                                .offset(sizeXState.value.dp - 27.dp, sizeYState.value.dp - 35.dp)
                                .size(20.dp)
                                .pointerInput(Unit) {
                                    detectDragGestures { change, dragAmount ->
                                        change.consume()
                                        notesViewModel.updateCoordinates(
                                            noteModel, width, height, dragAmount, lifecycleScope
                                        )
                                    }
                                }
                        )
                        (LocalContext.current as MainActivity).apply {
                            Icon(
                                Icons.Rounded.Share, null, modifier = Modifier
                                    .offset(sizeXState.value.dp - 55.dp, 4.dp)
                                    .size(20.dp)
                                    .noRippleClickable {
                                        if (textState.value
                                                .replace(" ", "")
                                                .isNotEmpty()
                                        ) {
                                            filesProvider.apply {
                                                share(
                                                    saveText(
                                                        timestampChangeState.value,
                                                        textState.value
                                                    )
                                                )
                                            }
                                        } else longToast("The note is empty")
                                    }, tint = White
                            )
                        }
                        Icon(
                            Icons.Rounded.Fullscreen, null, modifier = Modifier
                                .offset(sizeXState.value.dp - 28.dp, 3.dp)
                                .size(24.dp)
                                .noRippleClickable {
                                    notesViewModel.noteId = id
                                    navController.navigate("fullScreenNote")
                                }, tint = White
                        )

                        Icon(
                            Icons.Rounded.Remove, null, modifier = Modifier
                                .offset(sizeXState.value.dp - 80.dp, 4.dp)
                                .size(20.dp)
                                .noRippleClickable {
                                    notesViewModel.deleteNoteById(lifecycleScope, id)
                                }, tint = White
                        )
                    }
                }
            }
        }
    }
}






