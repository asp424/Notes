package com.lm.notes.ui.bars

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.AuthIcon
import com.lm.notes.ui.cells.CanvasCircle
import com.lm.notes.ui.cells.DownloadIcon
import com.lm.notes.ui.cells.LogOutIcon
import com.lm.notes.ui.cells.ReversLayoutIcon
import com.lm.notes.ui.cells.SettingsIcon
import com.lm.notes.ui.cells.SortIcon

@Composable
fun DefaultBar(animScale: Float) {
    with(mainDep) {
        val dens = LocalDensity.current
        with(dens) {
            (width - 55.dp).also { x ->
                CanvasCircle(
                    x - infoOffset.value,
                    animScale, (width / 25.toDp()).dp,
                    Red
                )
                CanvasCircle(
                    x + infoOffset.value,
                    animScale, (width / 25.toDp()).dp, White
                )
            }
            LogOutIcon(animScale)
            SettingsIcon()
            SortIcon(animScale)
            ReversLayoutIcon(animScale)
            Row {
                DownloadIcon()
                AuthIcon(animScale)
            }
        }
    }

}