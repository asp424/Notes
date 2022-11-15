package com.lm.notes.ui.cells

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.TextFieldValue
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.lm.notes.data.local_data.NoteData
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.enterLeftToRight
import com.lm.notes.ui.exitRightToLeft
import com.lm.notes.ui.screens.FullScreenNote

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavHost() {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                AnimatedNavHost(mainDep.navController, "mainList") {
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

