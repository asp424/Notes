package com.lm.notes.ui.cells.bars

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.Arrangement.Start
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.icons.IconClipBoard
import com.lm.notes.utils.listIconsClipboard

@Composable
fun ClipboardBar() {
    with(mainDep) {
        with(notesViewModel.uiStates) {
            notesViewModel.noteModelFullScreen.value.text.apply {
                val configuration = LocalConfiguration.current
                Card(
                    Modifier.height(if (configuration.orientation == ORIENTATION_PORTRAIT
                            ) 45.dp else 0.dp)
                        .padding(1.dp)
                        .fillMaxWidth(),
                    RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp),
                    colors = CardColors(getMainColor, Black, Black, Black)
                ) {
                    Box(Modifier.padding(start = 10.dp), Alignment.Center) {
                        Row(Modifier, Start, Top) {
                            listIconsClipboard.forEach { icon ->
                                IconClipBoard(icon, getTextIsEmpty, getSecondColor)
                            }
                        }
                    }
                }
            }
        }
    }
}



