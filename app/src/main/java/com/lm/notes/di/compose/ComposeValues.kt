package com.lm.notes.di.compose

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.presentation.ViewModels
import javax.inject.Inject

interface ComposeValues {

    @Composable
    fun mainScreenValues(
    ): MainDeps

    class Base @Inject constructor(
        private val sPreferences: SPreferences
    ) : ComposeValues {

        @Composable
        override fun mainScreenValues() = with(LocalConfiguration.current) {
            MainDeps(
                _width = screenWidthDp.dp,
                _height = screenHeightDp.dp,
                _iconUri = remember { mutableStateOf(checkNotNull(sPreferences.readIconUri())) },
                _progressVisibility = remember { mutableStateOf(false) },
                _infoVisibility = remember { mutableStateOf(false) },
                _coroutine = rememberCoroutineScope()
            ).apply {
                animateDpAsState(
                    if (infoVisibility) 20.dp else 0.dp, tween(500)
                ).setInfoOffset
            }
        }
    }
}
