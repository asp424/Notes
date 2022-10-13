package com.lm.notes.ui.cells

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.toHtml
import androidx.core.widget.addTextChangedListener
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.MainActivity
import com.lm.notes.utils.log
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener


@Composable
fun EditText() {
    mainDep.apply {
        with(notesViewModel) {
            with(notesViewModel.uiStates) {
                notesViewModel.editTextController.apply {
                    var scale by remember { mutableStateOf(1f) }
                    val state = rememberTransformableState { zoomChange, _, _ ->
                        scale *= zoomChange
                    }
                    val activity = LocalContext.current as MainActivity
                    val owner = LocalLifecycleOwner.current
                    remember {
                        setEventListener(activity, owner,
                            KeyboardVisibilityEventListener {
                                it.log
                                if (!it && !getIsFormatMode) editText.clearFocus()
                            })
                        true
                    }

                    val textListener = remember {
                        editText.run {
                            addTextChangedListener {
                                updateNoteFromUi(text.toHtml())
                                if (fromHtml(
                                        noteModelFullScreen.value.text
                                            .replace(" ", "", false)
                                    ).isNotEmpty()
                                ) true.setTextIsEmpty else false.setTextIsEmpty
                                noteModelFullScreen.value.text = text.toString()
                            }
                        }
                    }
                    LocalDensity.current.apply {
                        Box(
                            Modifier
                                .border(1.dp, Black)
                                .transformable(state)
                                .fillMaxSize()
                                .graphicsLayer { editText.textSize = scale * 16 },
                            Alignment.TopStart
                        ) {
                            AndroidView(
                                { editText },
                                Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .alpha(0.8f)
                            )
                        }
                        DisposableEffect(true) {
                            onDispose { editText.removeTextChangedListener(textListener) }
                        }
                    }
                }
            }
        }
    }
}

