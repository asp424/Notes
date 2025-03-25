package com.lm.notes.ui.cells.icons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.modifiers.noRippleClickable

@Composable
fun IconFormat(source: ImageVector) {
    with(mainDep.notesViewModel) {
        Box(Modifier.padding(start = 10.dp)) {
            with(uiStates) {
                val values = source.getButtonFormatValues()
                Icon(
                    source, null,
                    Modifier.noRippleClickable(
                        remember {
                        { with(editTextController) { values.second.buttonFormatAction() } }
                    }), values.first
                )
            }
        }
    }
}


