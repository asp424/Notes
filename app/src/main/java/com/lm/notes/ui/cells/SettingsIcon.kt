package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.animScale
import com.lm.notes.utils.noRippleClickable

@Composable
fun SettingsIcon() {
    with(mainDep.notesViewModel.uiStates) {
        Icon(Icons.Rounded.Settings, null, modifier = Modifier
            .scale(animScale(getIsMainMode))
            .noRippleClickable {
               if (getSettingsVisible) false.setSettingsVisible else true.setSettingsVisible
            }
            .offset((-20).dp, 3.dp), tint = Color.White
        )
    }
}