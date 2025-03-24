package com.lm.notes.ui.cells


import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.notes.data.local_data.ShareType
import com.lm.notes.di.compose.MainDependencies

@Composable
fun MainDependencies.ShareButtonsCell(shareType: ShareType) =
    FloatingActionButton(
        {},
        Modifier
            .size(25.dp)
            .iconVisibility(notesViewModel.uiStates.getNoteMode, 600),
        containerColor = if (shareType == ShareType.Null) notesViewModel.uiStates.getMainColor
        else White
    )
    {
        Text(
            shareType.type, color = Black, textAlign = TextAlign.Center,
            fontSize = if (shareType == ShareType.AsHtml) 10.sp else 12.sp
        )
    }
