package com.lm.notes.ui.cells

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
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun SortIcon(animScale: Float) {
    with(mainDep) {
            Box(modifier = Modifier.padding(start = 15.dp)) {
                Icon(
                    Icons.AutoMirrored.Sharp.Sort, null,
                    modifier = Modifier
                        .size(28.dp)
                        .scale(animScale)
                        .noRippleClickable(
                            remember {
                                {
                                    notesViewModel.sortByCreate()
                                }
                            }), tint = notesViewModel.uiStates.getSecondColor
                )
        }
    }
}