package com.lm.notes.ui.bars

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.theme.bar
import com.lm.notes.utils.animScale

@Composable
fun TopBar(isFullScreen: Boolean) {
    with(mainDep) {
        notesViewModel.noteModelFullScreen.value.textState.value.apply {
            TopAppBar(backgroundColor = bar, modifier = Modifier.fillMaxWidth()) {
                DefaultBar(animScale(!isFullScreen))
                FullScreenBar(
                    animScale(
                        isFullScreen && spansProvider.fromHtml(
                            replace(" ", "", false)
                        )?.isNotEmpty() ?: false
                    )
                )
              //  DeleteBar(animScale(notesViewModel.isDeleteMode))
            }
        }
    }
}
