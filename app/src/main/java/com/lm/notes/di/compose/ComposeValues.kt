package com.lm.notes.di.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import javax.inject.Inject

interface ComposeValues {

    @Composable
    fun mainScreenValues(
    ): MainDeps

    class Base @Inject constructor() : ComposeValues {

        @Composable
        override fun mainScreenValues() = with(LocalConfiguration.current) {
            with(LocalDensity.current) {
                MainDeps(
                    _width = remember {
                        mutableStateOf(90f)},
                        _height = remember {
                            mutableStateOf(screenHeightDp.dp.toPx() / 3)
                        },
                        _offset = remember { mutableStateOf(Offset.Zero) },
                        _scaleX = rememberSaveable { mutableStateOf(90f) },
                        _scaleY = rememberSaveable { mutableStateOf(20f) },
                        _eventOffset = remember { mutableStateOf(Offset.Zero) },
                        _listPoints = remember { mutableStateListOf() },
                        _action = remember { mutableStateOf(-1) },
                        _startMove = remember { mutableStateOf(false) },
                        _strike = remember { mutableStateOf(false) },
                        )
                    }
            }
        }
    }