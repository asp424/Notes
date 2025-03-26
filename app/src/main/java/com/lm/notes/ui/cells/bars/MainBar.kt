package com.lm.notes.ui.cells.bars

import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.ui.cells.AuthBox
import com.lm.notes.ui.cells.LogOutBox
import com.lm.notes.ui.cells.icons.MainBarIcon
import com.lm.notes.utils.animDp
import com.lm.notes.utils.forEachInList
import com.lm.notes.ui.cells.icons.icons_lists.listIconsMainBar

@Composable
fun MainDependencies.MainBar() {
    Row(
        Modifier
            .size(animDp(nVM.uiStates.getIsMainMode, width, 0.dp))
            .padding(start = 20.dp, top = 20.dp, end = 20.dp), SpaceBetween
    )
    {
        Row { listIconsMainBar.forEachInList { MainBarIcon(first, second) } }
        Box(Modifier.offset(0.dp, (-5).dp)) {
            LogOutBox()
            AuthBox()
        }
    }
}


