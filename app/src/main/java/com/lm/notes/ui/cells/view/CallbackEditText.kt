package com.lm.notes.ui.cells.view

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem

class CallbackEditText: ActionMode.Callback {
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?) = true
    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        menu?.clear(); return true
    }
    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = true
    override fun onDestroyActionMode(mode: ActionMode?) {}
}