package com.lm.notes.ui.cells

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.enterLeftToRight
import com.lm.notes.ui.enterRightToLeft
import com.lm.notes.ui.exitRightToLeft
import com.lm.notes.ui.screens.FullScreenNote
import kotlinx.coroutines.launch

@Composable
fun NavHostAnim() {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                val coroutineScope = rememberCoroutineScope()
                NavHost(mainDep.navController, "mainList") {
                    composable("mainList", enterTransition = { enterLeftToRight },
                        exitTransition = { exitRightToLeft }) {
                        MainColumn()
                        LaunchedEffect(true) {
                            setMainMode(); notesViewModel.sortByChange()
                        }
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

