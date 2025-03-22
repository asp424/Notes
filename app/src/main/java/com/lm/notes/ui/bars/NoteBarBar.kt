package com.lm.notes.ui.bars

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Translate
import androidx.compose.material.icons.rounded.Widgets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lm.notes.data.local_data.ShareType
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.FullScreenIcon
import com.lm.notes.ui.cells.ShareCanvasButton

@Composable
fun NoteBar() {
    Row(Modifier.offset((-120).dp, 0.dp)) {
        Row(Modifier.offset(0.dp, 10.dp)) {
            LocalDensity.current.apply {
                ShareCanvasButton(160.dp, ShareType.AsHtml, mainDep.width)
                ShareCanvasButton(195.dp, ShareType.AsTxt, mainDep.width)
                ShareCanvasButton(230.dp, start = mainDep.width)
            }
        }
        listIconsFullScreen.forEach {
            FullScreenIcon(it,
                with(mainDep.notesViewModel.uiStates) { getSecondColor })
        }
    }
}

val listIconsFullScreen by lazy {
    listOf(
        Icons.Rounded.Share,
        Icons.Rounded.Widgets,
        Icons.Rounded.Translate,
        Icons.Rounded.Save
    )
}

