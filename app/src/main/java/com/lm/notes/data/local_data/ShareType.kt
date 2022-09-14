package com.lm.notes.data.local_data

sealed class ShareType {
    object AsTxt: ShareType()
    object AsHtml: ShareType()
}

fun ShareType.type() = when(this){
    is ShareType.AsTxt -> ".txt"
    is ShareType.AsHtml -> ".html"
}