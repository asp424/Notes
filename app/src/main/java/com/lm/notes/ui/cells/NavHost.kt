package com.lm.notes.ui.cells

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.enterLeftToRight
import com.lm.notes.ui.enterRightToLeft
import com.lm.notes.ui.exitLeftToRight
import com.lm.notes.ui.exitRightToLeft
import com.lm.notes.ui.screens.FullScreenNote

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavHost(
    noteModel: NoteModel,
    isFormatMode: Boolean,
    onNavigate: (Boolean) -> Unit
) {
    with(mainDep) {
        AnimatedNavHost(
            navController = navController,
            startDestination = "mainList"
        ) {
            composable("mainList", enterTransition = { enterLeftToRight },
                exitTransition = { exitRightToLeft }) {
                MainColumn()
                onNavigate(false)
            }

            composable("fullScreenNote", enterTransition = { enterRightToLeft },
                exitTransition = { exitLeftToRight }) {
                FullScreenNote(noteModel)
                onNavigate(true)
            }
        }
    }
}