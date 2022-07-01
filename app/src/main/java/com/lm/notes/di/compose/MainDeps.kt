package com.lm.notes.di.compose

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp

data class MainDeps(
    private val _width: MutableState<Dp>,
    private val _height: MutableState<Dp>,
    private val _iconUri: MutableState<Uri>,
    private val _progressVisibility: MutableState<Boolean>,
    private val _distance: MutableState<Float> = mutableStateOf(0f),
    private val _figureLength: MutableState<Int> = mutableStateOf(0),
    private val _scaleX: MutableState<Float>,
    private val _scaleY: MutableState<Float>,
    private val _eventOffset: MutableState<Offset>,
    private val _listPoints: SnapshotStateList<Offset>,
    private val _action: MutableState<Int>,
    private val _startMove: MutableState<Boolean>,
    private val _strike: MutableState<Boolean>
) {
    val Uri.setIconUri get() = run { _iconUri.value = this }
    val Boolean.setProgressVisibility get() = run { _progressVisibility.value = this }
    val Float.setScaleX get() = run { _scaleX.value = this }
    val Float.setScaleY get() = run { _scaleY.value = this }
    val Offset.setEventOffset get() = run { _eventOffset.value = this }
    val Int.setAction get() = run { _action.value = this }
    val Boolean.setStartMove get() = run { _startMove.value = this }
    val Boolean.setStrike get() = run { _strike.value = this }
    val Float.setDistance get() = run { _distance.value = this }
    val Int.setFigureLength get() = run { _figureLength.value = this }
    val Dp.setWidth get() = run { _width.value = this }
    val Dp.setHeight get() = run { _height.value = this }

    val progressVisibility get() = _progressVisibility.value
    val iconUri get() = _iconUri.value
    val scaleX get() = _scaleX.value
    val scaleY get() = _scaleY.value
    val eventOffset get() = _eventOffset.value
    val action get() = _action.value
    val startMove get() = _startMove.value
    val strike get() = _strike.value
    val listPoints get() = _listPoints
    val figureLength get() = _figureLength.value
    val distance get() = _distance.value
    val width get() = _width.value
    val height get() = _height.value
}
