package com.lm.notes.ui.cells.bars

import androidx.compose.foundation.layout.Arrangement.Start
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.notes.data.models.UiStates
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.icons.DeleteBarIcon
import com.lm.notes.ui.cells.icons.icons_lists.listOfDeleteBarIcon
import com.lm.notes.utils.animDp
import com.lm.notes.utils.forEachInList
import com.lm.notes.utils.modifiers.paddingInt

@Composable
fun UiStates.DeleteBar() = Row(
    Modifier
        .fillMaxWidth()
        .paddingInt(start = 20, end = 20, top = 20)
        .size(animDp(getIsDeleteMode, mainDep.width, 0.dp)), Start
) {
    Row { listOfDeleteBarIcon.forEachInList { DeleteBarIcon(first, second) } }
}


