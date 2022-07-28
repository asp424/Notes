package com.lm.notes.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lm.notes.data.models.NoteModel
import com.lm.notes.di.compose.MainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun FullScreenBar(noteModel: NoteModel) {
    with(noteModel) {
        with(MainDep.mainDep) {
            Icon(
                Icons.Rounded.Share, null, modifier = Modifier
                    .size(25.dp).noRippleClickable {
                        filesProvider.apply {
                            share(saveText(timestampChangeState.value, textState.value))
                        }
                    }, tint = Color.White
            )
        }
    }
}
