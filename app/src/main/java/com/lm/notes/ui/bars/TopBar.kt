package com.lm.notes.ui.bars

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.animScale
import com.lm.notes.utils.noRippleClickable

@Composable
fun TopBar() {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                TopAppBar(backgroundColor = getMainColor, modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable { false.setSettingsVisible }) {
                    DefaultBar(animScale(getIsMainMode))
                    FullScreenBar(
                        animScale(getIsFullscreenMode && getTextIsEmpty),
                        animScale(
                            getIsFullscreenMode && getTextIsEmpty && getNotShareVisible
                        )
                    )
                    DeleteBar(animScale(getIsDeleteMode))
                }
            }
        }
    }
}
