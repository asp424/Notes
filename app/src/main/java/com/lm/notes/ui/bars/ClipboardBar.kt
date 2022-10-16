package com.lm.notes.ui.bars

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Start
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.IconClipBoard
import com.lm.notes.utils.log

@Composable
fun ClipboardBar() {
    with(mainDep) {
        with(notesViewModel.uiStates) {
            notesViewModel.noteModelFullScreen.value.text.apply {
                Card(
                    Modifier.height(45.dp).padding(1.dp).fillMaxWidth(),
                    RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp), getMainColor
                ) {
                    Box(
                        Modifier.padding(start = 10.dp), Alignment.Center
                    ) {
                        Row(
                            Modifier, Start, Top
                        ) { listIconsClipboard.forEach { IconClipBoard(it, getTextIsEmpty) } }
                    }
                }
            }
        }
    }
}

val listIconsClipboard
    by lazy {
        listOf(
            Icons.Rounded.ContentPaste,
            Icons.Rounded.SelectAll,
            Icons.Rounded.ContentCopy,
            Icons.Rounded.CopyAll,
            Icons.Rounded.ContentCut,
        )
    }

