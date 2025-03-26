package com.lm.notes.ui.cells.bars

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.icons.IconClipBoard
import com.lm.notes.ui.cells.icons.NoteBarIcon
import com.lm.notes.ui.cells.icons.icons_lists.listIconsClipboard
import com.lm.notes.ui.cells.icons.icons_lists.listIconsNote
import com.lm.notes.ui.cells.icons.icons_lists.listShareTypes
import com.lm.notes.ui.cells.shareButton
import com.lm.notes.utils.forEachInList

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun LandscapeBar() {
    with(mainDep) {
        with(nVM.uiStates) {
            val scrollState = rememberScrollState()
            Row {
                LocalDensity.current.apply {
                    Row { listIconsNote.subList(1, 4).forEachInList { NoteBarIcon(first, second) } }
                    Row {
                        Box {
                            listShareTypes.forEachInList { shareButton(this) }
                            with(listIconsNote[0]) { NoteBarIcon(first, second) }
                        }
                    }
                }
            }
            Column(
                Modifier
                    .iconVisibility(!getIsMainMode)
                    .verticalScroll(scrollState)
                    .padding(bottom = 40.dp)
                    .scale(0.8f)
            ) {
                listIconsNote.forEachInList {
                    NoteBarIcon(first, second)
                }
                Column(Modifier.offset((-5).dp, 0.dp)) {
                    listIconsClipboard.forEach { im ->
                        IconClipBoard(im, getTextIsEmpty, getMainColor, 2.dp)
                    }
                }
            }
        }
    }
}