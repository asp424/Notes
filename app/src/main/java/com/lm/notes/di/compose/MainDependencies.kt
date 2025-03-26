package com.lm.notes.di.compose

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.data.local_data.FilesProvider
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.ui.cells.view.app_widget.NoteAppWidgetController

data class MainDependencies(
    val width: Dp,
    val height: Dp,
    val density: Density,
    val nVM: NotesViewModel,
    val firebaseAuth: FirebaseAuth,
    val sPreferences: SPreferences,
    val filesProvider: FilesProvider,
    val navController: NavHostController,
    val noteAppWidgetController: NoteAppWidgetController,
) {
    @Composable
    fun Modifier.iconVisibility(target: Boolean, duration: Int = 300) = composed {
        scale(animVisibility(target, duration))
    }
}

@Composable
fun animVisibility(target: Boolean, duration: Int = 300) =
    animateFloatAsState(if (target) 1f else 0f, tween(duration)).value

@SuppressLint("ContextCastToActivity")
@Composable
fun MainScreenDependencies(
    sPreferences: SPreferences,
    firebaseAuth: FirebaseAuth,
    filesProvider: FilesProvider,
    noteAppWidgetController: NoteAppWidgetController,
    nVM: NotesViewModel,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        Local provides MainDependencies(
            width = LocalConfiguration.current.screenWidthDp.dp,
            height = LocalConfiguration.current.screenHeightDp.dp,
            density = LocalDensity.current,
            nVM = remember { nVM },
            firebaseAuth = remember { firebaseAuth },
            sPreferences = remember { sPreferences },
            filesProvider = remember { filesProvider },
            navController = rememberNavController(),
            noteAppWidgetController = remember { noteAppWidgetController }
        ), content = content
    )
}

private val Local = staticCompositionLocalOf<MainDependencies> { error("No value provided") }

object MainDep {
    val mainDep @Composable get() = Local.current
}

