package com.lm.notes.ui.cells.bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.notes.data.local_data.ShareType
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.ui.cells.ShareButtonsCell
import com.lm.notes.ui.cells.icons.NoteBarIcon
import com.lm.notes.utils.animDp
import com.lm.notes.utils.forEachInList
import com.lm.notes.utils.listIconsNote
import com.lm.notes.utils.paddingInt

@Composable
fun MainDependencies.NoteBar() {

    Row(
        Modifier
            .fillMaxWidth()
            .paddingInt(start = 20, end = 20, top = 20)
            .size(animDp(notesViewModel.uiStates.getNoteMode, width, 0.dp)),
        Arrangement.SpaceBetween
    ) {
        Row {
            Box {
                ShareButtonsCell(ShareType.AsTxt)
                ShareButtonsCell(ShareType.AsHtml)
                ShareButtonsCell(ShareType.TextPlain)
                ShareButtonsCell(ShareType.Null)
                with(listIconsNote[0]) { NoteBarIcon(first, second) }
            }
        }
        Row { listIconsNote.subList(1, 4).forEachInList { NoteBarIcon(first, second) } }
    }
}





