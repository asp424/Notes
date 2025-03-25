package com.lm.notes.ui.cells


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lm.notes.data.local_data.ShareType
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.presentation.MainActivity
import com.lm.notes.utils.getHeader

@SuppressLint("ContextCastToActivity")
@Composable
fun MainDependencies.shareButton(shareType: ShareType) = with(notesViewModel) {
    notesViewModel.noteModelFullScreen.collectAsState().value.apply {
        val activity = LocalContext.current as MainActivity
        val coroutine = rememberCoroutineScope()
        with(filesProvider) {
            FloatingActionButton(
                {
                    val header = with(header) {
                        getHeader(isNewHeader(this))
                    }
                    when(shareType){
                        is ShareType.TextPlain -> shareAsText(text, activity)
                        is ShareType.Null -> Unit
                        else -> shareAsFile(shareType, header, activity)
                    }
                    uiStates.expandShare(coroutine)
                },
                Modifier
                    .size(25.dp)
                    .iconVisibility(notesViewModel.uiStates.getNoteMode, 600),
                containerColor = if (shareType == ShareType.Null) notesViewModel.uiStates.getMainColor
                else White
            )
            {
                Text(
                    shareType.text, color = Black, textAlign = TextAlign.Center,
                    fontSize = shareType.fontSize
                )
            }
        }
    }
}