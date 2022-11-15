package com.lm.notes.ui.bars

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement.Start
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ClearAll
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.ContentCut
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material.icons.rounded.SelectAll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.IconClipBoard

@Composable
fun ClipboardBar() {
    with(mainDep) {
        with(notesViewModel.uiStates) {
            notesViewModel.noteModelFullScreen.value.text.apply {
                val configuration = LocalConfiguration.current

                    Configuration.ORIENTATION_PORTRAIT
                Card(
                    Modifier
                        .height(if(configuration.orientation
                            == Configuration.ORIENTATION_PORTRAIT ) 45.dp else 20.dp)
                        .padding(1.dp)
                        .fillMaxWidth(),
                    RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp), getMainColor
                ) {
                    Box(
                        Modifier.padding(start = 10.dp), Alignment.Center
                    ) {
                        Row(
                            Modifier, Start, Top
                        ) {
                            listIconsClipboard.forEach {
                                IconClipBoard(
                                    it,
                                    getTextIsEmpty
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

val listIconsClipboard
        by lazy {
            listOf(
                Icons.Rounded.ClearAll,
                Icons.Rounded.ContentPaste,
                Icons.Rounded.SelectAll,
                Icons.Rounded.ContentCopy,
                Icons.Rounded.CopyAll,
                Icons.Rounded.ContentCut,
            )
        }

