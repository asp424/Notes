package com.lm.notes.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatUnderlined
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.LightGray
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
import com.lm.notes.ui.theme.gray
import com.lm.notes.ui.theme.green
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

                val visibilityUnderlineButtonOffset by animateFloatAsState(
                    if (isSelected.value) 1f else 0f, tween(400)
                )

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
                            .background(gray, RoundedCornerShape(10.dp))
                    ) {
                        CustomTextField(noteModel)
                        Text(
                            text = formatTimestamp(timestampChangeState.value),
                            fontStyle = FontStyle.Italic, fontSize = 11.sp,
                            modifier = Modifier
                                .padding(2.dp)
                                .offset(2.dp, sizeYState.value.dp - 32.dp),
                            style = TextStyle(fontWeight = FontWeight.Bold), color = Black
                        )
                        underlinedMap.log
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
                                    }, tint = Black
                            )
                        }
                        Icon(
                            Icons.Rounded.Fullscreen, null, modifier = Modifier
                                .offset(sizeXState.value.dp - 28.dp, 3.dp)
                                .size(24.dp)
                                .noRippleClickable {
                                    notesViewModel.noteId = id
                                    navController.navigate("fullScreenNote")
                                }, tint = Black
                        )

                        Icon(
                            Icons.Rounded.Remove, null, modifier = Modifier
                                .offset(sizeXState.value.dp - 80.dp, 4.dp)
                                .size(20.dp)
                                .noRippleClickable {
                                    notesViewModel.deleteNoteById(lifecycleScope, id)
                                }, tint = Black
                        )
                        Icon(
                            Icons.Rounded.FormatUnderlined, null, modifier = Modifier
                                .offset(
                                    sizeXState.value.dp - 24.dp, 40.dp
                                )
                                .size(20.dp).scale(visibilityUnderlineButtonOffset)
                                .noRippleClickable {
                                    if (!checkForIntersects(selectedTextRange, underlinedMap.value)) {
                                        underlinedMap.value =
                                            "s${selectedTextRange.start}u${selectedTextRange.end}e"
                                    } else
                                        underlinedMap.value = "sEu0e"
                                }, tint = if (checkForIntersects(selectedTextRange, underlinedMap.value))
                                    green else Black
                        )
                    }
                }
            }
        }
    }
}






