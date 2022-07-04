package com.lm.notes.di.compose

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope

data class MainDeps(
    private val _width: Dp,
    private val _height: Dp,
    private val _iconUri: MutableState<Uri>,
    private val _infoVisibility: MutableState<Boolean>,
    private val _infoOffset: MutableState<Dp> = mutableStateOf(0.dp),
    private val _progressVisibility: MutableState<Boolean>,
    private val _coroutine: CoroutineScope
) {
    val Uri.setIconUri get() = run { _iconUri.value = this }
    val Boolean.setProgressVisibility get() = run { _progressVisibility.value = this }
    val State<Dp>.setInfoOffset get() = run { _infoOffset.value = this.value }
    val Boolean.setInfoVisibility get() = run { _infoVisibility.value = this }

    val progressVisibility get() = _progressVisibility.value
    val infoVisibility get() = _infoVisibility.value
    val infoOffset get() = _infoOffset.value
    val iconUri get() = _iconUri.value
    val width get() = _width
    val height get() = _height
    val coroutine get() = _coroutine
}
