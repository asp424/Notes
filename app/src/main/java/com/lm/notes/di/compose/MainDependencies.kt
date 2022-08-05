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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.data.local_data.FilesProvider
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.presentation.ViewModelFactory
import com.lm.notes.utils.format_text.ClipboardProvider
import com.lm.notes.utils.format_text.TextFormatter
import kotlinx.coroutines.CoroutineScope

data class MainDependencies(
    val width: Dp,
    val height: Dp,
    var iconUri: MutableState<Uri>,
    val infoVisibility: MutableState<Boolean>,
    val infoOffset: State<Dp>,
    val progressVisibility: MutableState<Boolean>,
    val coroutine: CoroutineScope,
    val notesViewModel: NotesViewModel,
    val firebaseAuth: FirebaseAuth,
    val sPreferences: SPreferences,
    val listState: LazyListState,
    val filesProvider: FilesProvider,
    val navController: NavHostController,
    val textFormatter: TextFormatter,
    val clipboardProvider: ClipboardProvider
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun mainScreenDependencies(
    sPreferences: SPreferences,
    viewModelFactory: ViewModelFactory,
    firebaseAuth: FirebaseAuth,
    filesProvider: FilesProvider,
    content: @Composable () -> Unit
) = with(LocalConfiguration.current) {

    val infoVisibility = remember { mutableStateOf(false) }
    LocalViewModelStoreOwner.current?.also { ownerVM ->

        val notesViewModel = remember {
            ViewModelProvider(ownerVM, viewModelFactory)[NotesViewModel::class.java]
        }

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

                notesViewModel = notesViewModel,
                firebaseAuth = firebaseAuth,
                sPreferences = sPreferences,
                listState = rememberLazyListState(),
                filesProvider = filesProvider,
                navController = rememberAnimatedNavController(),
                textFormatter = TextFormatter.Base(notesViewModel),
                clipboardProvider = ClipboardProvider.Base(
                    LocalClipboardManager.current, notesViewModel
                )
            ), content = content
        )
    }
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
        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
    }
}


