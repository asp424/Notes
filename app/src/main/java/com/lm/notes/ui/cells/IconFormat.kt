package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun IconFormat(source: ImageVector) {
    with(mainDep.notesViewModel) {
        Box(modifier = Modifier.padding(start = 10.dp)) {
            Icon(
                source, null,
                modifier = Modifier.noRippleClickable {
                    with(editTextController) { source.buttonFormatAction() }
                },
                tint = with(uiStates) { source.getTint() }
            )
        }
    }
}


