package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.notes.di.compose.MainDep.mainDep

@Composable
fun Note(
    modifier: Modifier,
    time: String,
    notesText: String,
    header: String,
    id: String,
    timeCreate: String
) {
    val dens = LocalDensity.current
    with(dens) {
        with(mainDep) {
            with(nVM.uiStates) {
                Card(
                    modifier = modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(8.dp), border = getNoteCardBorder(id),
                    elevation = getNoteCardElevation(id)
                ) {
                    Box(Modifier.padding(10.dp), CenterStart) {
                        Column {
                            NoteCardText(header)
                            Text(
                                text = notesText.ifEmpty { ".........................." },
                                maxLines = 1,
                                fontSize =
                                if (notesText.isEmpty()) 26.sp else 12.sp,
                                color = Color.Gray
                            )
                            NoteCardText(time, false)
                        }
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(top = 10.dp, end = 10.dp),
                            contentAlignment = BottomEnd
                        ) {
                           CanvasCircle(0.dp, 1f, (width / 4.toDp()).dp, getMainColor)
                            Text(
                                "создано",
                                Modifier.offset(10.dp, 5.dp),
                                color = getSecondColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                timeCreate,
                                Modifier.offset(10.dp, 20.dp),
                                color = getSecondColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )

                        }
                    }
                }
            }
        }
    }
}

