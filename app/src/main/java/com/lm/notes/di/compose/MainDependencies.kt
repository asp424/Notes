package com.lm.notes.di.compose

import android.net.Uri
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.data.local_data.FilesProvider
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.presentation.ViewModelFactory
import com.lm.notes.utils.log
import kotlinx.coroutines.CoroutineScope

data class MainDependencies(
    val width: Dp,
    val height: Dp,
    var iconUri: MutableState<Uri>,
    val infoVisibility: MutableState<Boolean>,
    val infoOffset: State<Dp>,
    val progressVisibility: MutableState<Boolean>,
    val coroutine: CoroutineScope,
    val viewModelFactory: ViewModelFactory,
    val firebaseAuth: FirebaseAuth,
    val sPreferences: SPreferences,
    val listState: LazyListState,
    val filesProvider: FilesProvider,
    val navController: NavHostController
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreenDependencies(
    sPreferences: SPreferences, viewModelFactory: ViewModelFactory, firebaseAuth: FirebaseAuth,
    filesProvider: FilesProvider, content: @Composable () -> Unit
) = with(LocalConfiguration.current) {

        val infoVisibility = remember { mutableStateOf(false) }

        CompositionLocalProvider(
            Local provides MainDependencies(
                width = screenWidthDp.dp,
                height = screenHeightDp.dp,
                iconUri = remember { mutableStateOf(checkNotNull(sPreferences.readIconUri())) },
                progressVisibility = remember { mutableStateOf(false) },
                infoVisibility = infoVisibility,
                coroutine = rememberCoroutineScope(),
                infoOffset = animateDpAsState(
                    if (infoVisibility.value) 20.dp else 0.dp, tween(500)
                ),

                viewModelFactory = viewModelFactory,
                firebaseAuth = firebaseAuth,
                sPreferences = sPreferences,
                listState = rememberLazyListState(),
                filesProvider = filesProvider,
                navController = rememberAnimatedNavController()
            ), content = content
        )
    }

private val Local = staticCompositionLocalOf<MainDependencies> { error("No value provided") }

object MainDep {
    val mainDep @Composable get() = Local.current
}

class CustomTextToolbar(private val view: View) : TextToolbar {
    private var actionMode: ActionMode? = null
    override var status: TextToolbarStatus = TextToolbarStatus.Hidden
        private set

    override fun hide() {
        status = TextToolbarStatus.Hidden
        actionMode?.finish()
        actionMode = null
    }

    override fun showMenu(
        rect: Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?
    ) {
        view.startActionMode(TextActionModeCallback())
    }
}

class TextActionModeCallback(
) : ActionMode.Callback {

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        mode?.title  .log
        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.title  .log
        return false
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.title  .log
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        mode?.title  .log
    }
}


