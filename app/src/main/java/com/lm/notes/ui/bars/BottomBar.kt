package com.lm.notes.ui.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lm.notes.data.models.NavControllerScreens
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
                            NavControllerScreens.Note.setNavControllerScreen
                            }
                            addNewNote(lifecycleScope) {
                                editTextController.setNewText("")
                                checkForEmptyText()
                            }
                        }
                    }

                Box(
                    modifier = Modifier
                        .fillMaxSize().alpha(0.8f)
                        .offset(0.dp, animDp(getIsMainMode, 0.dp, 200.dp)),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Box(Modifier.background(getMainColor).fillMaxWidth().height(70.dp))
                    Box(Modifier.padding(bottom = 40.dp, end = 40.dp)) {
                        Button({}, Modifier.scale(1.30f).size(60.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = White)
                        ) {}

                        Button({}, Modifier.scale(1.05f).size(60.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = getSecondColor)
                        ) {}
                        Button(click, Modifier.size(60.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = getMainColor)
                        ) {
                            Text("+", fontSize = 25.sp, color = getSecondColor,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
