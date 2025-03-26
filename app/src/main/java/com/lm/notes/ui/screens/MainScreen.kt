package com.lm.notes.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.LifecycleCoroutineScope
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.presentation.MainActivity
import com.lm.notes.ui.cells.BackgroundImage
import com.lm.notes.ui.cells.NavHostAnim
import com.lm.notes.ui.cells.SettingsCard
import com.lm.notes.ui.cells.bars.BottomBar
import com.lm.notes.ui.cells.bars.FormatBar
import com.lm.notes.ui.cells.bars.TopBarFromOrientation
import com.lm.notes.utils.backPressHandle
import kotlinx.coroutines.launch

@Composable
fun MainDependencies.MainScreen(mA: MainActivity, lifecycleScope: LifecycleCoroutineScope) {
    BackgroundImage()
    Column { TopBarFromOrientation(); NavHostAnim() }
    BottomBar()
    FormatBar()
    SettingsCard()
    BackHandler(true, remember { { lifecycleScope.launch { backPressHandle(nVM, mA) } } })
    nVM.uiStates.NavControllerController()
}
