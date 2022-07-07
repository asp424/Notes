package com.lm.notes.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.lm.notes.di.compose.ComposeDependencies
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.presentation.ViewModels
import javax.inject.Inject

interface Screens {

    @Composable
    fun MainScreen()

    class Base @Inject constructor(
        private val viewModels: ViewModels,
        private val topBar: TopBar,
        private val stateReceiver: StateReceiver
    ) : Screens {

        @Composable
        override fun MainScreen() {
            LocalViewModelStoreOwner.current?.also { owner ->
                val notesViewModel = remember {
                    viewModels.viewModelProvider(owner)[NotesViewModel::class.java]
                }
                val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

                Column {
                    topBar.Default()
                    stateReceiver.Default()
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(onClick = {
                        notesViewModel.newNote("test", lifecycleScope)
                    }) {
                        Text(text = "new")
                    }
                }
            }
        }
    }
}
