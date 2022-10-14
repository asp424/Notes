package com.lm.notes.ui.cells

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.MainActivity
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

@Composable
fun KeyBoardListener() {
    with(mainDep.notesViewModel) {
        with(uiStates) {
            with(editTextController.editText) {
                val activity = LocalContext.current as MainActivity
                val owner = LocalLifecycleOwner.current
                remember {
                    KeyboardVisibilityEvent.setEventListener(activity, owner) {
                        if (!it && !getIsFormatMode) clearFocus()
                    }
                    true
                }
            }
        }
    }
}