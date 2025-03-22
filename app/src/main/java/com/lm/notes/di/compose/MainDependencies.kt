package com.lm.notes.di.compose

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.data.local_data.FilesProvider
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.presentation.MainActivity
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.presentation.ViewModelFactory
import com.lm.notes.ui.cells.view.app_widget.NoteAppWidgetController
import kotlinx.coroutines.CoroutineScope

data class MainDependencies(
    val width: Dp,
    val height: Dp,
    val density: Density,
    var iconUri: MutableState<Uri>,
    val authButtonMenuVisibility: MutableState<Boolean>,
    val authButtonMenuOffsetY: Dp,
    val progressVisibility: MutableState<Boolean>,
    val coroutine: CoroutineScope,
    val notesViewModel: NotesViewModel,
    val firebaseAuth: FirebaseAuth,
    val sPreferences: SPreferences,
    val listState: LazyListState,
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

fun animVisibility(target: Boolean, duration: Int = 300)
= animateFloatAsState(if (target) 1f else 0f, tween(duration)).value

@SuppressLint("ContextCastToActivity")
@Composable
fun MainScreenDependencies(
    sPreferences: SPreferences,
    viewModelFactory: ViewModelFactory,
    firebaseAuth: FirebaseAuth,
    filesProvider: FilesProvider,
    noteAppWidgetController: NoteAppWidgetController,
    content: @Composable () -> Unit
) = with(LocalConfiguration.current) {
    val authButtonMenuVisibility = remember { mutableStateOf(false) }
    val context = LocalContext.current as MainActivity
    val owner = LocalViewModelStoreOwner.current ?: context
    CompositionLocalProvider(
        Local provides MainDependencies(
            width = screenWidthDp.dp,
            height = screenHeightDp.dp,
            density = LocalDensity.current,
            iconUri = remember { mutableStateOf(checkNotNull(sPreferences.readIconUri())) },
            progressVisibility = remember { mutableStateOf(false) },
            authButtonMenuVisibility = authButtonMenuVisibility,
            coroutine = rememberCoroutineScope(),
            authButtonMenuOffsetY = animateDpAsState(
                if (authButtonMenuVisibility.value) 20.dp else 0.dp, tween(500)
            ).value,
            notesViewModel = remember {
                ViewModelProvider(owner, viewModelFactory)[NotesViewModel::class.java]
            },
            firebaseAuth = remember { firebaseAuth },
            sPreferences = remember { sPreferences },
            listState = rememberLazyListState(),
            filesProvider = remember { filesProvider },
            navController = rememberNavController(),
            noteAppWidgetController = remember { noteAppWidgetController }
        ).apply {
            if (iconUri.toString().isNotEmpty()) {
                progressVisibility.value = false
                iconUri.value = sPreferences.readIconUri() ?: Uri.EMPTY
            }
        }, content = content
    )
}

private val Local = staticCompositionLocalOf<MainDependencies> { error("No value provided") }

object MainDep {
    val mainDep @Composable get() = Local.current
}

