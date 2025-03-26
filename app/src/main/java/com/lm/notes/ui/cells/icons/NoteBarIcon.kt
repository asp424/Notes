package com.lm.notes.ui.cells.icons

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.utils.modifiers.noRippleClickable

@Composable
fun MainDependencies.NoteBarIcon(
    icon: ImageVector,
    offsetX: Dp = 0.dp
) = with(nVM.uiStates) {
    Icon(
        icon, null,
        Modifier
            .size(25.dp)
            .offset(offsetX, 0.dp)
            .noRippleClickable(
                icon.getNoteBarIconsActions(
                    rememberCoroutineScope(),
                    noteAppWidgetController,
                    nVM.noteModelFullScreen.value,
                    nVM.editTextController
                ).second
            )
            .iconVisibility(getNoteMode && getTextIsEmpty, 600), getSecondColor
    )
}

