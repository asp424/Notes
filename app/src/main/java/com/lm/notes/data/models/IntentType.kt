package com.lm.notes.data.models

enum class IntentType(val type: String) {
    SendPlain("text/plain"), ViewPlain("file"),
    Content("content"), Word("application/msword")
}