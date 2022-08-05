package com.lm.notes.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowDownward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lm.notes.R
import com.lm.notes.di.compose.MainDep.mainDep
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen() {
    with(mainDep) {
        var visibleBottomBar by remember { mutableStateOf(true) }

        var isAuthIconVisibility by remember { mutableStateOf(true) }

        Image(
            painter = painterResource(id = R.drawable.notebook_list), null,
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop
        )

        Column {
            TopBar(isAuthIconVisibility)
            NavController {
                coroutine.launch {
                    visibleBottomBar = it
                    delay(350)
                    isAuthIconVisibility = it
                }
            }
        }

        BottomBar(visibleBottomBar)
        val isLarge by remember { mutableStateOf(false) }

        val scale by animateFloatAsState(if (isLarge) 1f else 0f)

        Box(
            Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp, end = 60.dp)
                .scale(scale),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                    coroutine.launch {
                        listState.animateScrollToItem(notesViewModel.notesList.value.lastIndex)
                    }
                }, shape = CircleShape
            ) {
                Icon(Icons.Sharp.ArrowDownward, contentDescription = null)
            }
        }
    }
}
