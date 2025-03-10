package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Public
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.animScale
import com.lm.notes.utils.noRippleClickable

@Composable
fun DownloadIcon() {
    val lifecycleScope = androidx.lifecycle.compose.LocalLifecycleOwner.current.lifecycleScope

    with(mainDep) {
        Box(modifier = Modifier.padding(start = 15.dp)) {
            Icon(
                Icons.Rounded.Public, null,
                modifier = Modifier
                    .size(28.dp)
                    .scale(animScale(notesViewModel.uiStates.getIsAuth
                            && notesViewModel.uiStates.getIsMainMode))
                    .noRippleClickable(
                        remember {
                            {
                                notesViewModel.downloadNotesFromFirebase(lifecycleScope)
                            }
                        }), tint = notesViewModel.uiStates.getSecondColor
            )
        }
    }
}