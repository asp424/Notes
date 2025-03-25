package com.lm.notes.ui.cells.icons

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.modifiers.noRippleClickable

@Composable
fun DeleteBarIcon(icon: ImageVector, offsetY: Dp = 0.dp) {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                Icon(
                    icon, null, Modifier
                        .iconVisibility(getIsDeleteMode)
                        .offset(x = offsetY).size(25.dp)
                        .noRippleClickable(icon.getDeleteBarIconsActions()), getSecondColor
                )
            }
        }
    }
}

