package com.lm.notes.ui.bars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.AuthIcon
import com.lm.notes.ui.cells.CanvasCircle
import com.lm.notes.ui.cells.LogOutIcon
import com.lm.notes.ui.cells.SettingsIcon
import com.lm.notes.utils.noRippleClickable

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

         Box(modifier = Modifier.padding(start = 15.dp)) {
            Icon(
                Icons.AutoMirrored.Sharp.Sort, null,
                modifier = Modifier
                    .size(28.dp).scale(animScale)
                    .noRippleClickable(
                        remember {
                            {
                                notesViewModel.sortByChange()
                            }
                        }), tint = White
            )
        }

        AuthIcon(animScale)
    }
}

