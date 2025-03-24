package com.lm.notes.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.lm.notes.ui.cells.bars.ClipboardBar
import com.lm.notes.ui.cells.bars.LandscapeBar
import com.lm.notes.ui.cells.EditText
import com.lm.notes.ui.cells.HeaderTextField

@Composable
fun FullScreenNote() {
    Column(Modifier.fillMaxSize()) {
        when (LocalConfiguration.current.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                    EditText(); LandscapeBar()
                }
            }

            else -> {
                ClipboardBar(); HeaderTextField(); EditText()
            }
        }

    }
}


