package com.lm.notes.ui.cells

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lm.notes.data.models.NavControllerScreens
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.ui.enterLeftToRight
import com.lm.notes.ui.enterRightToLeft
import com.lm.notes.ui.exitLeftToRight
import com.lm.notes.ui.exitRightToLeft
import com.lm.notes.ui.screens.FullScreenNote

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainDependencies.NavHostAnim() {
    with(notesViewModel) {
        with(uiStates) {
            NavHost(navController, NavControllerScreens.Main.screen) {
                composable(NavControllerScreens.Main.screen, enterTransition = { enterLeftToRight },
                    exitTransition = { exitRightToLeft }) {
                    MainColumn()
                    LaunchedEffect(true) {
                        setMainMode()
                        notesViewModel.sortByChange()
                    }
                }

                composable(NavControllerScreens.Note.screen, enterTransition = { enterRightToLeft },
                    exitTransition = { exitLeftToRight }, content = {
                        FullScreenNote()
                        LaunchedEffect(Unit) { setFullScreenMode() }
                    })
            }
        }
    }
}


@Composable
fun EnterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visibleState = MutableTransitionState(
            initialState = false
        ).apply { targetState = true },
        modifier = Modifier,
        enter = slideInVertically(
            initialOffsetY = { -40 }
        ) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
    ) {
        content()
    }
}

