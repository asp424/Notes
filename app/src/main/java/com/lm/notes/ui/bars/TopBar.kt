package com.lm.notes.ui.bars

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.utils.noRippleClickable

@Composable
fun MainDependencies.TopBar() {
    with(notesViewModel) {
        with(uiStates) {
            TopAppBar(
                backgroundColor = getMainColor, modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable(remember { { false.setSettingsVisible } })
            ) {
                Row(Modifier.padding(start = 20.dp)) {
                    MainBar()
                    NoteBar()
                    DeleteBar()
                }
            }
        }
    }
}
