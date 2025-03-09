package com.lm.notes.ui.bars

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.AuthIcon
import com.lm.notes.ui.cells.CanvasCircle
import com.lm.notes.ui.cells.LogOutIcon
import com.lm.notes.ui.cells.ReversLayoutIcon
import com.lm.notes.ui.cells.SettingsIcon
import com.lm.notes.ui.cells.SortIcon

@Composable
fun DefaultBar(animScale: Float) {
    with(mainDep) {
        (width - 55.dp).also { x ->
            CanvasCircle(
                x - infoOffset.value, animScale, infoOffset.value + 20.dp, Red
            )
            CanvasCircle(
                x + infoOffset.value, animScale, 45.dp, White
            )
        }

        LogOutIcon(animScale)
        SettingsIcon()
        SortIcon(animScale)
        ReversLayoutIcon(animScale)
        AuthIcon(animScale)
    }
}

