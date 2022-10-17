package com.lm.notes.ui.cells

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.enterLeftToRight
import com.lm.notes.ui.exitRightToLeft
import com.lm.notes.ui.screens.FullScreenNote

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavHost() {
    with(mainDep) {
        with(notesViewModel) {
            AnimatedNavHost(
                navController = mainDep.navController, startDestination = "mainList"
            ) {
                composable("mainList", enterTransition = { enterLeftToRight },
                    exitTransition = { exitRightToLeft }) {
                    MainColumn()
                    LaunchedEffect(true) {
                        uiStates.setMainMode()
                    }
                }

                composable("fullScreenNote", content = {
                    FullScreenNote()
                    LaunchedEffect(true) {
                        uiStates.setFullScreenMode()
                    }
                })
            }
        }
    }
}

