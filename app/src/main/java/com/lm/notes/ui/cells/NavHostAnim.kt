package com.lm.notes.ui.cells

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.enterLeftToRight
import com.lm.notes.ui.enterRightToLeft
import com.lm.notes.ui.exitRightToLeft
import com.lm.notes.ui.screens.FullScreenNote

@Composable
fun NavHostAnim() {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                NavHost(mainDep.navController, "mainList") {
                    composable("mainList", enterTransition = { enterLeftToRight },
                        exitTransition = { exitRightToLeft }) {
                        MainColumn()
                        LaunchedEffect(true) { setMainMode() }
                    }

                    composable("fullScreenNote", content = {
                        FullScreenNote()
                        LaunchedEffect(true) { setFullScreenMode() }
                    })
                }
            }
        }
    }
}

