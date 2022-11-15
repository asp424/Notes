package com.lm.notes.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.TextFieldValue
import com.lm.notes.ui.bars.ClipboardBar
import com.lm.notes.ui.cells.EditText
import com.lm.notes.ui.cells.HeaderTextField

@Composable
fun FullScreenNote() {
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            ClipboardBar()
            val configuration = LocalConfiguration.current
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {

                }
                else -> HeaderTextField()
            }
            EditText()
        }
    }
}


