package com.lm.notes.ui.bars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Translate
import androidx.compose.material.icons.rounded.Widgets
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lm.notes.data.local_data.ShareType
import com.lm.notes.ui.cells.FullScreenIcon
import com.lm.notes.ui.cells.ShareCanvasButton

@Composable
fun FullScreenBar() {
    LocalDensity.current.apply {
        ShareCanvasButton(160.dp, ShareType.AsHtml)
        ShareCanvasButton(195.dp, ShareType.AsTxt)
        ShareCanvasButton(230.dp)
        listIconsFullScreen.forEach { FullScreenIcon(it) }
    }
}

val listIconsFullScreen by lazy {
    listOf(Icons.Rounded.Share, Icons.Rounded.Widgets, Icons.Rounded.Translate)
}

