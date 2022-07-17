package com.lm.notes.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.NotesViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavController(onFullScreenNote: (Boolean) -> Unit) {
    with(mainDep) {
        LocalViewModelStoreOwner.current?.also { ownerVM ->
            val notesViewModel = remember {
                ViewModelProvider(ownerVM, viewModelFactory)[NotesViewModel::class.java]
            }

            AnimatedNavHost(
                navController = navController,
                startDestination = "mainList"
            ) {
                composable("mainList", enterTransition = {
                    enterLeftToRight
                },
                    exitTransition = {
                       exitRightToLeft
                    }) { MainColumn{
                        onFullScreenNote(it)
                } }

                composable("fullScreenNote", enterTransition = {
                    enterRightToLeft
                }, exitTransition = {
                        exitLeftToRight
                    }) { FullScreenNote{
                        onFullScreenNote(false)
                } }
            }
        }
    }
}