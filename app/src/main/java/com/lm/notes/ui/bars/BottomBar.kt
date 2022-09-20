package com.lm.notes.ui.bars

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.lifecycleScope
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.theme.bar
import com.lm.notes.utils.animDp

@Composable
fun BottomBar(isFullScreen: Boolean) {
    with(mainDep) {
        val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
        val click = remember {
            {
                notesViewModel.addNewNote(lifecycleScope) {
                    editTextProvider.setText("")
                    navController.navigate("fullScreenNote") {
                        popUpTo("mainList")
                    }
                }
            }
        }

        LocalDensity.current.apply {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(0.dp, animDp(!isFullScreen, 0.dp, 100.dp, 100))
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
                ) {}
                Button(
                    onClick = click, modifier = Modifier
                        .offset((width - 89.dp), (height - 79.dp))
                        .size(60.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = bar
                    )
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
