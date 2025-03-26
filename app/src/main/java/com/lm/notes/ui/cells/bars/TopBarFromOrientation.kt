package com.lm.notes.ui.cells.bars

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.AppBarDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.utils.modifiers.noRippleClickable

@Composable
fun MainDependencies.TopBarFromOrientation() {
    if (LocalConfiguration.current.orientation == ORIENTATION_PORTRAIT) TopBar()
    else {
        if (nVM.uiStates.getIsMainMode) TopBar()
    }
}

@Composable
fun TopBar() {
    with(mainDep) {
        with(nVM.uiStates) {
            TopAppBar(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .noRippleClickable(
                        remember { { false.setSettingsVisible } }
                    ), getMainColor,
                contentPadding = AppBarDefaults.ContentPadding
            ) {
                MainBar()
                Box {
                    NoteBar()
                    DeleteBar()
                }
            }
        }
    }
}