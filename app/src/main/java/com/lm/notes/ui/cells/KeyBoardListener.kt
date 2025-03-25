package com.lm.notes.ui.cells

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.lm.notes.presentation.MainActivity
import com.lm.notes.presentation.NotesViewModel
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

@SuppressLint("ContextCastToActivity")
@Composable
fun NotesViewModel.KeyBoardListener() {
    val activity = LocalContext.current as MainActivity
    val owner = LocalLifecycleOwner.current
    remember {
        KeyboardVisibilityEvent.setEventListener(activity, owner) {
            if (!it && !uiStates.getIsFormatMode) editTextController.editText.clearFocus()
        }
        true
    }
}