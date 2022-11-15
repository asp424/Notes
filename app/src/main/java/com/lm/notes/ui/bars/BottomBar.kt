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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.animDp

@Composable
fun BottomBar() {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
                val click = remember {
                    {
                        editTextController.createEditText()
                        if (!getIsDeleteMode) {
                                navController.navigate("fullScreenNote") {
                                    popUpTo("mainList")
                                }
                                addNewNote(lifecycleScope) {
                                    editTextController.setNewText("")
                                    checkForEmptyText()
                                }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(0.dp, animDp(getIsMainMode, 0.dp, 100.dp))
                ) {
                    Canvas(Modifier) {
                        drawRect(
                            getMainColor,
                            Offset(0f, height.value * density - 28.dp.toPx()),
                            size = Size(width.value * density + 200f, 60.dp.toPx())
                        )
                    }
                    Button(
                        onClick = {}, modifier = Modifier
                            .offset((width - 89.dp), (height - 61.dp))
                            .scale(1.05f)
                            .size(60.dp), colors = ButtonDefaults.buttonColors(
                            containerColor = getSecondColor
                        )
                    ) {}
                    Button(
                        onClick = click, modifier = Modifier
                            .offset((width - 89.dp), (height - 61.dp))
                            .size(60.dp), colors = ButtonDefaults.buttonColors(
                            containerColor = getMainColor
                        )
                    ) {
                        Text(
                            text = "+", fontSize = 25.sp, modifier = Modifier.offset(
                                (-1).dp,
                                (-1).dp
                            ), color = getSecondColor
                        )
                    }
                }
            }
        }
    }
}
