package com.lm.notes.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.theme.IconFormat

@Composable
fun FormatBar(isAuthIconVisibility: Boolean) {
    with(mainDep) {
        notesViewModel.noteModelFullScreen.collectAsState().value.apply {

            with(mainDep.clipboardProvider) {
                IconClipBoard(
                    Icons.Rounded.ContentPaste,
                    isSelected() == true && !isAuthIconVisibility
                )

                IconClipBoard(
                    Icons.Rounded.SelectAll,
                    textState.value.text.isNotEmpty() && !isAuthIconVisibility
                )

                IconClipBoard(
                    Icons.Rounded.ContentCopy,
                    textState.value.selection.length != 0
                )

                IconClipBoard(
                    Icons.Rounded.ContentCut,
                    textState.value.selection.length != 0
                )

                IconFormat(Icons.Rounded.FormatBold)

                IconFormat(Icons.Rounded.FormatItalic)

                IconFormat(Icons.Rounded.FormatUnderlined)
            }
        }
    }
}
