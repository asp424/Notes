package com.lm.notes.ui.cells.view

sealed interface LoadStatesEditText{

    object Loading: LoadStatesEditText

    object Success: LoadStatesEditText
}