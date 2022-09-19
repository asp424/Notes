package com.lm.notes.data.local_data

sealed class ShareType {
    object AsTxt: ShareType()
    object AsHtml: ShareType()

    val type get() = when(this){
        is AsTxt -> ".txt"
        is AsHtml -> ".html"
    }
}

