package com.lm.notes.ui.cells

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lm.notes.data.models.NavControllerScreens
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.ui.enterLeftToRight
import com.lm.notes.ui.enterRightToLeft
import com.lm.notes.ui.exitLeftToRight
import com.lm.notes.ui.exitRightToLeft
import com.lm.notes.ui.screens.NoteScreen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainDependencies.NavHostAnim() {
    with(notesViewModel) {
        with(uiStates) {
            NavHost(navController, NavControllerScreens.Main.screen) {
                composable(NavControllerScreens.Main.screen, enterTransition = { enterLeftToRight },
                    exitTransition = { exitRightToLeft }) {
                    NotesList()
                    LaunchedEffect(true) {
                        setMainMode()
                        notesViewModel.sortByChange()
                    }
                }

                composable(NavControllerScreens.Note.screen, enterTransition = { enterRightToLeft },
                    exitTransition = { exitLeftToRight }, content = {
                        NoteScreen()
                        LaunchedEffect(Unit) { setFullScreenMode() }
                    })
            }
        }
    }
}


