package com.lm.notes.ui.bars

import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.AuthBox
import com.lm.notes.ui.cells.LogOutBox
import com.lm.notes.ui.cells.icons.MainBarIcon
import com.lm.notes.utils.forEachInList
import com.lm.notes.utils.listIconsMainBar

@Composable
fun MainBar() {
    with(mainDep) {
        Row(Modifier.fillMaxSize(), SpaceBetween, CenterVertically) {
            Row(Modifier.padding(top = 10.dp)) {
                listIconsMainBar.forEachInList { MainBarIcon(first, second) }
            }
            Box(Modifier.padding(top = 5.dp, end = 20.dp)) {
                LogOutBox(30.dp)
                AuthBox(30.dp)
            }
        }
    }
}

