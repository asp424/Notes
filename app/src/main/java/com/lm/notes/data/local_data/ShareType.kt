package com.lm.notes.data.local_data

import androidx.compose.ui.unit.sp

sealed class ShareType {
    data object AsTxt: ShareType()
    data object AsHtml: ShareType()
    data object TextPlain: ShareType()
    data object Null: ShareType()

    val text get() = when(this){
        is AsTxt -> ".txt"
        is AsHtml -> ".html"
        is TextPlain -> "text"
        is Null -> ""
    }

    val fontSize get() = when(this){
        is AsHtml -> 10.sp
        else -> 12.sp
    }
}

