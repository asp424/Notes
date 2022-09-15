package com.lm.notes.ui.screens

import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker
import com.godaddy.android.colorpicker.toColorInt
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.bars.FormatBar
import com.lm.notes.ui.cells.EditText
import com.lm.notes.ui.cells.HeaderTextField
import com.lm.notes.ui.cells.IconFormat
import com.lm.notes.utils.animDp

@Composable
fun FullScreenNote(
    noteModel: NoteModel
) {
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize()) {
                HeaderTextField(noteModel)
                EditText()
            }
        }
    }
