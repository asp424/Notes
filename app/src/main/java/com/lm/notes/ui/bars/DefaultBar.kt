package com.lm.notes.ui.bars

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.AuthIcon
import com.lm.notes.ui.cells.CanvasCircle
import com.lm.notes.ui.cells.LogOut
import com.lm.notes.ui.cells.SettingsIcon

@Composable
fun DefaultBar(animScale: Float) {
    with(mainDep) {
        (width - 55.dp).also { x ->
                CanvasCircle(
                    x - infoOffset.value, animScale, infoOffset.value + 20.dp, Red
                )
                CanvasCircle(
                    x + infoOffset.value, animScale, 15.dp, White
                )
            }
        LogOut(animScale)
        SettingsIcon()
        AuthIcon(animScale)
    }
}
