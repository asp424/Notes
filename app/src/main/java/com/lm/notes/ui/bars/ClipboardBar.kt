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
import com.lm.notes.ui.theme.bar

@Composable
fun ClipboardBar() {
    with(mainDep) {
        notesViewModel.noteModelFullScreen.value.textState.value.apply {
                val textIsEmpty = notesViewModel.spansProvider.fromHtml(
                    replace(" ", "", false)
                )?.isNotEmpty() ?: false
            Card(
                Modifier
                    .height(45.dp)
                    .padding(1.dp)
                    .fillMaxWidth(),
                RoundedCornerShape(
                    topEnd = 0.dp,
                    topStart = 0.dp,
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp
                ),
                backgroundColor = bar
            ) {
                Box(
                    Modifier.padding(start = 10.dp), Alignment.Center
                ) {
                    Row(
                        Modifier, Start, Top) { listIconsClipboard.forEach { IconClipBoard(it, textIsEmpty) } }
                }
            }
        }
    }
}

val listIconsClipboard
    get() = listOf(
        Icons.Rounded.ContentPaste,
        Icons.Rounded.SelectAll,
        Icons.Rounded.ContentCopy,
        Icons.Rounded.CopyAll,
        Icons.Rounded.ContentCut,
    )

