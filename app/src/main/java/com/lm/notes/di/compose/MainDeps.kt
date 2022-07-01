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
    private val _infoHeightStart: MutableState<Dp> = mutableStateOf(0.dp),
    private val _infoHeightEnd: MutableState<Dp> = mutableStateOf(0.dp),
    private val _progressVisibility: MutableState<Boolean>,
    private val _coroutine: CoroutineScope
) {
    val Uri.setIconUri get() = run { _iconUri.value = this }
    val Boolean.setProgressVisibility get() = run { _progressVisibility.value = this }
    val State<Dp>.setInfoHeightStart get() = run { _infoHeightStart.value = this.value }
    val State<Dp>.setInfoHeightEnd get() = run { _infoHeightEnd.value = this.value }
    val Boolean.setInfoVisibility get() = run { _infoVisibility.value = this }

    val progressVisibility get() = _progressVisibility.value
    val infoVisibility get() = _infoVisibility.value
    val infoHeightStart get() = _infoHeightStart.value
    val infoHeightEnd get() = _infoHeightEnd.value
    val iconUri get() = _iconUri.value
    val width get() = _width
    val height get() = _height
    val coroutine get() = _coroutine
}
