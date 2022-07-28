package com.lm.notes.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.lm.notes.di.compose.MainDep.mainDep

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavController(onFullScreenNote: (Boolean) -> Unit) {
    with(mainDep) {
        AnimatedNavHost(
            navController = navController,
            startDestination = "mainList"
        ) {
            composable("mainList", enterTransition = {
                enterLeftToRight
            },
                exitTransition = {
                    exitRightToLeft
                }) {
                MainColumn {
                    onFullScreenNote(it)
                }
            }

            composable("fullScreenNote", enterTransition = {
                enterRightToLeft
            }, exitTransition = {
                exitLeftToRight
            }) {
                FullScreenNote {
                    onFullScreenNote(false)
                }
            }
        }
    }
}
