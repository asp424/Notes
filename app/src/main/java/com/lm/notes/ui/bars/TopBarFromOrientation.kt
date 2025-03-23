package com.lm.notes.ui.bars

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.lm.notes.data.models.UiStates
import com.lm.notes.utils.noRippleClickable

@Composable
fun UiStates.TopBarFromOrientation() {
    if (LocalConfiguration.current.orientation == ORIENTATION_PORTRAIT) TopBar()
    else {
        if (getIsMainMode) TopBar()

    }
}
@Composable
fun UiStates.TopBar() {
    TopAppBar(
        Modifier
            .fillMaxWidth()
            .noRippleClickable(remember { { false.setSettingsVisible } }),
        getMainColor
    ) {
        Row(Modifier.padding(start = 20.dp)) {
            MainBar()
            NoteBar()
            DeleteBar()
        }
    }
}
