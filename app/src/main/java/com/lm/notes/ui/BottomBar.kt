package com.lm.notes.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.ui.theme.bar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BottomBar(visibleBottomBar: Boolean) {
    with(mainDep) {
        LocalViewModelStoreOwner.current?.also { owner ->
            val notesViewModel = remember {
                ViewModelProvider(owner, viewModelFactory)[NotesViewModel::class.java]
            }
            val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
            val bottomBarOffset by animateDpAsState(
                if (visibleBottomBar) 0.dp else 100.dp, tween(700)
            )

            LocalDensity.current.apply {
                Box(
                    modifier = Modifier
                        .fillMaxSize().offset(0.dp, bottomBarOffset)
                        .alpha(0.8f)
                ) {
                    Canvas(Modifier) {
                        drawRect(
                            bar,
                            Offset(0f, height.value * density - 50.dp.toPx()),
                            size = Size(width.value * density, 50.dp.toPx())
                        )
                    }
                    Button(
                        onClick = {}, modifier = Modifier
                            .offset((width - 89.dp), (height - 79.dp))
                            .scale(1.05f)
                            .size(60.dp), colors = ButtonDefaults.buttonColors(
                            containerColor = White
                        )
                    ) {
                    }
                    Button(
                        onClick = {
                            notesViewModel.addNewNote(lifecycleScope, width.value, height.value)
                            var sumHeight = 0f
                            notesViewModel.notesList.value
                                .forEach {
                                    sumHeight += it.sizeYState.value
                                }
                            coroutine.launch {
                                delay(200); listState.animateScrollBy(
                                (sumHeight * density) + 60 * density * notesViewModel.notesList.value.size,
                                tween(1000)
                            )
                            }
                        }, modifier = Modifier
                            .offset((width - 89.dp), (height - 79.dp))
                            .size(60.dp)
                    ) {
                        Text(
                            text = "+", fontSize = 25.sp, modifier = Modifier.offset(
                                (-1).dp,
                                (-1).dp
                            )
                        )
                    }
                }
            }
        }
    }
}
