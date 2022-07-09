package com.lm.notes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import javax.inject.Inject

interface Screens {

    @Composable
    fun MainScreen()

    class Base @Inject constructor(
        private val topBar: TopBar,
        private val bottomBar: BottomBar,
        private val mainColumn: MainColumn
    ) : Screens {

        @Composable
        override fun MainScreen() {

            Column {
                topBar.Default()
                mainColumn.Default()
            }
            bottomBar.Default()
        }
    }
}
