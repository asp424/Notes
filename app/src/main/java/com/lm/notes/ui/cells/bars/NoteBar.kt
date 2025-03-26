package com.lm.notes.ui.cells.bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.ui.cells.icons.NoteBarIcon
import com.lm.notes.ui.cells.icons.icons_lists.listIconsNote
import com.lm.notes.ui.cells.icons.icons_lists.listShareTypes
import com.lm.notes.ui.cells.shareButton
import com.lm.notes.utils.animDp
import com.lm.notes.utils.forEachInList
import com.lm.notes.utils.modifiers.paddingInt

@Composable
fun MainDependencies.NoteBar() {

    Row(
        Modifier
            .fillMaxWidth()
            .paddingInt(start = 40, end = 20, top = 20)
            .size(animDp(nVM.uiStates.getNoteMode, width, 0.dp)),
        Arrangement.SpaceBetween
    ) {
        Row { listIconsNote.subList(1, 4).forEachInList { NoteBarIcon(first, second) } }
        Row {
            Box {
                listShareTypes.forEachInList { shareButton(this) }
                with(listIconsNote[0]) { NoteBarIcon(first, second) }
            }
        }
    }
}





