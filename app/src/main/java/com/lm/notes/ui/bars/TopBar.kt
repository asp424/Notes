package com.lm.notes.ui.bars

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.animScale
import com.lm.notes.utils.noRippleClickable

@Composable
fun TopBar() {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {

                TopAppBar(
                    backgroundColor = getMainColor, modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable(remember { { false.setSettingsVisible } })
                ) {
                    DefaultBar(animScale(getIsMainMode))
                    FullScreenBar()
                    DeleteBar(animScale(getIsDeleteMode))
                }
            }
        }
    }
}
