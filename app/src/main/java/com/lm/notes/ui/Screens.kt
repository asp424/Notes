package com.lm.notes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import javax.inject.Inject

interface Screens {

    @Composable
    fun MainScreen()

    class Base @Inject constructor(
    ) : Screens {

        @Composable
        override fun MainScreen() {
            Column(Modifier.fillMaxSize()) {

            }
        }
    }
}
