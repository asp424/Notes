package com.lm.notes.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.pointerInput
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
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.ui.theme.bar
import com.lm.notes.utils.formatTimestamp
import com.lm.notes.utils.log
import com.lm.notes.utils.noRippleClickable
import java.util.*

@Composable
fun Note(noteModel: NoteModel) {
    with(noteModel) {
        with(mainDep) {
            LocalViewModelStoreOwner.current?.also { ownerVM ->
                val notesViewModel = remember {
                    ViewModelProvider(ownerVM, viewModelFactory)[NotesViewModel::class.java]
                }

                val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

                LaunchedEffect(true) {
                    isChanged = false
                }

                val initTimeStampChange = remember { timestampChangeState.value }

                Box(
                    modifier = Modifier
                        .width(sizeXState.value.dp)
                        .height(sizeYState.value.dp)
                        .padding(bottom = 10.dp)
                        .background(bar)
                        .shadow(30.dp)
                ) {
                    TextField(
                        value = textState.value,
                        onValueChange = { str ->
                            textState.value = str
                            if (!isChanged) isChanged = true
                            if (text != textState.value)
                                timestampChangeState.value = Calendar.getInstance().time.time
                            else timestampChangeState.value = initTimeStampChange
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = White
                        ), modifier = Modifier
                            .fillMaxSize()
                            .padding(1.dp)
                    )
                    Text(
                        text = formatTimestamp(timestampChangeState.value),
                        fontStyle = FontStyle.Italic, fontSize = 11.sp,
                        modifier = Modifier
                            .padding(2.dp)
                            .offset(
                                0.dp,
                                sizeYState.value.dp - 30.dp
                            ), style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.hand), null,
                        modifier = Modifier
                            .offset(sizeXState.value.dp - 25.dp, sizeYState.value.dp - 34.dp)
                            .size(20.dp)
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    if (!isChanged) isChanged = true
                                    change.consume()
                                    if (sizeXState.value.dp + dragAmount.x.dp in width - 200.dp..width - 40.dp
                                    ) sizeXState.value += dragAmount.x

                                    if (sizeYState.value.dp + dragAmount.y.dp in 100.dp..height - 80.dp
                                    ) sizeYState.value += dragAmount.y
                                }
                            }
                    )

                    Icon(
                        Icons.Rounded.Remove, null, modifier = Modifier
                            .offset(sizeXState.value.dp - 25.dp, 1.dp)
                            .size(20.dp)
                            .noRippleClickable {
                                notesViewModel.deleteNoteById(lifecycleScope, id)
                            }
                    )
                }
            }
        }
    }
}





