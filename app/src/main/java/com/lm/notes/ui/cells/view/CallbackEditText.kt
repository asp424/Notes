package com.lm.notes.ui.cells.view

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.get
import com.lm.notes.utils.log

class CallbackEditText(
) : ActionMode.Callback {

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?) =
        mode?.menu?.get(0)?.itemId != 16908322

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = true.apply { menu?.clear() }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = true

    override fun onDestroyActionMode(mode: ActionMode?) {
      //  editTextController.onDestroyContextMenu()
    }
}