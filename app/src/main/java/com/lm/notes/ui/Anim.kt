package com.lm.notes.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry


val AnimatedContentTransitionScope<NavBackStackEntry>.enterUpToDown
    get() = slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(350))

val AnimatedContentTransitionScope<NavBackStackEntry>.enterDownToUp
    get() = slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(100))

val AnimatedContentTransitionScope<NavBackStackEntry>.enterLeftToRight
    get() = slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(100))

val AnimatedContentTransitionScope<NavBackStackEntry>.enterRightToLeft
    get() = slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(100))

val AnimatedContentTransitionScope<NavBackStackEntry>.exitDownToUp
    get() = slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, animationSpec = tween(500))

val AnimatedContentTransitionScope<NavBackStackEntry>.exitUpToDown
    get() = slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(500))

val AnimatedContentTransitionScope<NavBackStackEntry>.exitLeftToRight
    get() = slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(100))

val AnimatedContentTransitionScope<NavBackStackEntry>.exitRightToLeft
    get() = slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(100))
