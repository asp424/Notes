package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.lm.notes.data.local_data.NoteData.Base.Companion.NEW_TAG
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.theme.gray

@Composable
fun HeaderTextField(noteModel: NoteModel) {
    with(mainDep) {
        with(noteModel) {
            TextField(value =
            if (notesViewModel.isNewHeader(headerState.value.text)) TextFieldValue("")
            else headerState.value,
                onValueChange = {
                    notesViewModel.updateHeaderFromUi(it)
                },
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    backgroundColor = gray
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
                        text = headerState.value.text.substringAfter(NEW_TAG),
                        color = LightGray
                    )
                }
            )
        }
    }
}