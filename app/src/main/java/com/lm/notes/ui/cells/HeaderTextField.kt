package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.lm.notes.data.local_data.NoteData.Base.Companion.NEW_TAG
import com.lm.notes.di.compose.MainDep.mainDep

@Composable
fun HeaderTextField() {
    with(mainDep) {
        val noteModel by nVM.noteModelFullScreen.collectAsState()
        var header by remember(noteModel) {
            mutableStateOf(
                TextFieldValue(
                    if (noteModel.header.startsWith(NEW_TAG)) "" else noteModel.header
                )
            )
        }

        TextField(
            header,
            onValueChange = {
                nVM.updateHeaderFromUi(it)
                header = it
            },
            colors = TextFieldDefaults.colors().copy(
                unfocusedContainerColor = White,
                focusedContainerColor = White,
            ),
            modifier = Modifier.width(width),
            maxLines = 1,
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold, fontSize = 18.sp
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            placeholder = {
                Text(
                    text = noteModel.header.substringAfter(NEW_TAG),
                    color = LightGray
                )
            })
    }
}