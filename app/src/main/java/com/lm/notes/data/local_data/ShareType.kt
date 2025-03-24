package com.lm.notes.data.local_data

sealed class ShareType {
    data object AsTxt: ShareType()
    data object AsHtml: ShareType()
    data object TextPlain: ShareType()
    data object Null: ShareType()

    val type get() = when(this){
        is AsTxt -> ".txt"
        is AsHtml -> ".html"
        is TextPlain -> "text"
        is Null -> ""
    }
}

