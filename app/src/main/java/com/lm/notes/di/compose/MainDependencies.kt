package com.lm.notes.di.compose

import android.net.Uri
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.presentation.ViewModelFactory
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
    val sPreferences: SPreferences
)

@Composable
fun MainScreenDependencies(sPreferences: SPreferences, viewModelFactory: ViewModelFactory, firebaseAuth: FirebaseAuth, content: @Composable () -> Unit) =
    with(LocalConfiguration.current) {
        var infoVisibility = remember { mutableStateOf(false) }
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
                sPreferences = sPreferences
            ), content = content
        )
    }

private val Local = staticCompositionLocalOf<MainDependencies> { error("No value provided") }

object MainDep {
    val mainDep @Composable get() = Local.current
}
