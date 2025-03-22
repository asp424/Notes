package com.lm.notes.ui.bars

import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.Sort
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.AuthBox
import com.lm.notes.ui.cells.LogOutBox
import com.lm.notes.ui.cells.MainBarIcon

@Composable
fun MainBar() {
    with(mainDep) {
        Row(Modifier.fillMaxSize(), SpaceBetween, CenterVertically) {
            Row(Modifier.padding(top = 10.dp)) {
                MainBarIcon(Icons.Rounded.Settings)
                MainBarIcon(Icons.AutoMirrored.Sharp.Sort, 10.dp)
                MainBarIcon(Icons.Rounded.SwapVert, 20.dp)
                MainBarIcon(Icons.Rounded.Public, 30.dp)
            }
            Box(Modifier.padding(end = 20.dp)) {
                LogOutBox(30.dp)
                AuthBox(30.dp)
            }
        }
    }
}