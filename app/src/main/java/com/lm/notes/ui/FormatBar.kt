package com.lm.notes.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatBold
import androidx.compose.material.icons.rounded.FormatUnderlined
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lm.notes.data.models.NoteModel
import com.lm.notes.ui.theme.green
import com.lm.notes.utils.noRippleClickable

@Composable
fun FormatBar(noteModel: NoteModel) {
    with(noteModel) {
        Icon(
            Icons.Rounded.FormatBold,
            null,
            modifier = Modifier
                .size(25.dp)
                .noRippleClickable {
                    if (isHaveSelected(selectedTextRange.value, formatList(boldMap.value, ':'))) {
                        deselectAll(selectedTextRange.value, boldMap, ":")
                    } else
                        (selectedTextRange.value.start until selectedTextRange.value.end)
                            .forEach {
                                boldMap.value = boldMap.value + ":$it:"
                            }
                },
            tint = if (isHaveSelected(
                    selectedTextRange.value,
                    formatList(boldMap.value, ':')
                )
            ) green else Color.White
        )

        Icon(
            Icons.Rounded.FormatUnderlined,
            null,
            modifier = Modifier
                .size(25.dp)
                .noRippleClickable {
                    if (isHaveSelected(selectedTextRange.value, formatList(underlinedMap.value, '@'))) {
                        deselectAll(selectedTextRange.value, underlinedMap, "@")
                    } else
                        (selectedTextRange.value.start until selectedTextRange.value.end)
                            .forEach {
                                underlinedMap.value = underlinedMap.value + "@$it@"
                            }
                },
            tint = if (isHaveSelected(
                    selectedTextRange.value,
                    formatList(underlinedMap.value, '@')
                )
            ) green else Color.White
        )
    }
}
