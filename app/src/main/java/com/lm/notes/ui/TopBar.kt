package com.lm.notes.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.End
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.ui.theme.bar

@Composable
fun TopBar(isAuthIconVisibility: Boolean) {
    with(mainDep) {
        Box(Modifier.background(Color.White)) {
            Box(
                Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .background(bar)
            )
            DefaultBar(animateFloatAsState(
                if (isAuthIconVisibility) 1f else 0f, tween(350)
            ).value)
            LocalViewModelStoreOwner.current?.also { ownerVM ->
                val notesViewModel = remember {
                    ViewModelProvider(ownerVM, viewModelFactory)[NotesViewModel::class.java]
                }

                notesViewModel.notesList.collectAsState().value.also { list ->
                    list.find { it.id == notesViewModel.noteId }?.apply {
                        Box(
                            modifier = Modifier.scale(animateFloatAsState(
                                if (isAuthIconVisibility) 0f else 1f, tween(350)
                            ).value), contentAlignment = Alignment.Center
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .scale(
                                        animateFloatAsState(
                                            if (
                                                isSelected.value || textState.value.isEmpty()
                                            ) 0f else 1f, tween(350)
                                        ).value
                                    ).padding(end = 20.dp, top = 15.dp),
                                verticalAlignment = Alignment.CenterVertically
                                , horizontalArrangement = End
                            ) {
                                FullScreenBar(this@apply)
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .scale(
                                        animateFloatAsState(
                                            if (isSelected.value) 1f else 0f, tween(350)
                                        ).value
                                    ).padding(end = 20.dp, top = 15.dp),
                                verticalAlignment = Alignment.CenterVertically
                                , horizontalArrangement = End
                            ) {
                                FormatBar(this@apply)
                            }
                        }
                    }
                }
            }
        }
    }
}
