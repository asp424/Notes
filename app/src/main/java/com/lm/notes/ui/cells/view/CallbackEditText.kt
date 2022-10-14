package com.lm.notes.ui.cells.view

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.get
import com.lm.notes.data.models.UiStates
import com.lm.notes.utils.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class CallbackEditText (
    private val uiStates: UiStates,
    private val editTextController: EditTextController,
    private val editText: EditText
) : ActionMode.Callback {

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?) =
        (mode?.menu?.get(0)?.itemId != 16908322).apply {
        }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = true.apply {
       menu?.clear()
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = true

    override fun onDestroyActionMode(mode: ActionMode?) {
        CoroutineScope(Main).launch {
            with(uiStates) {
            editText.clearFocus()
            delay(200)
                if (getIsFormatMode && getSetSelectionEnable) {
                    "fuck".log
                    editTextController.setEditMode(); onClickEditText()
                }
            }
        }
    }
}