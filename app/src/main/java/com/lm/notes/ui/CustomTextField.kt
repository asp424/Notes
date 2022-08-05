package com.lm.notes.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep
import com.lm.notes.utils.format_text.TextFormatter.Base.Companion.BOLD
import com.lm.notes.utils.format_text.TextFormatter.Base.Companion.ITALIC
import com.lm.notes.utils.format_text.TextFormatter.Base.Companion.UNDERLINE

@Composable
fun CustomTextField(noteModel: NoteModel, padding: Dp = 28.dp) {
    with(noteModel) {
        val initTimeStampChange = remember { timestampChangeState.value }
        with(MainDep.mainDep) {
            TextField(
                value = textState.value,
                onValueChange = { str ->
                    notesViewModel.updateData(noteModel, initTimeStampChange, str)
                },
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    backgroundColor = Color.White
                ),
                placeholder = {
                    if (padding == 0.dp) Text(
                        text = "Note...",
                        color = LightGray
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
                    .border(BorderStroke(1.dp, Black))
                    .padding(padding),
                visualTransformation = with(textFormatter) {
                    transformText(
                        formattedIndicesList(boldMap.value, BOLD),
                        formattedIndicesList(underlinedMap.value, UNDERLINE),
                        formattedIndicesList(italicMap.value, ITALIC)
                    )
                },
            )
        }
    }
}









