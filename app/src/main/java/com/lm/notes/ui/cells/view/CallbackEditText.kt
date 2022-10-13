package com.lm.notes.ui.cells.view

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.lm.notes.data.models.UiStates
import com.lm.notes.utils.log
import javax.inject.Inject

class CallbackEditText @Inject constructor(
    private val uiStates: UiStates,
    private val inputMethodManager: InputMethodManager,
    private val editText: EditText
) : ActionMode.Callback {

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?) = false

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false.apply {
        menu?.clear()
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = false

    override fun onDestroyActionMode(mode: ActionMode?) {
    }
}