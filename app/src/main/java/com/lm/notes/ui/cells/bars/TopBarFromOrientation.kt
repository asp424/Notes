package com.lm.notes.ui.cells.bars

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.utils.noRippleClickable

@Composable
fun MainDependencies.TopBarFromOrientation() {
    if (LocalConfiguration.current.orientation == ORIENTATION_PORTRAIT) TopBar()
    else {
        if (notesViewModel.uiStates.getIsMainMode) TopBar()
    }
}

@Composable
fun MainDependencies.TopBar() = with(notesViewModel.uiStates) {
    TopAppBar(
        Modifier
            .fillMaxWidth().height(60.dp)
            .noRippleClickable(remember { { false.setSettingsVisible } }), getMainColor,
        contentPadding = AppBarDefaults.ContentPadding
    ) {
        MainBar()
        NoteBar()
        DeleteBar()
    }
}
