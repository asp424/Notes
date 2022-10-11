package com.lm.notes.ui.bars

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.theme.bar
import com.lm.notes.utils.animScale

@Composable
fun TopBar() {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                noteModelFullScreen.collectAsState().value.textState.value.apply {
                    TopAppBar(backgroundColor = bar, modifier = Modifier.fillMaxWidth()) {
                        DefaultBar(animScale(getIsMainMode))
                        FullScreenBar(
                            animScale(
                                getIsFullscreenMode && spansProvider.fromHtml(
                                    replace(" ", "", false)
                                )?.isNotEmpty() ?: false
                            )
                        )
                        DeleteBar(animScale(getIsDeleteMode))
                    }
                }
            }
        }
    }
}
