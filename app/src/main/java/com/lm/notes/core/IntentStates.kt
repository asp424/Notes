package com.lm.notes.core

import android.net.Uri

sealed class IntentStates {
    class SendPlain(val text: String): IntentStates()
    class ViewPlain(val uri: Uri?): IntentStates()
    class Word(val inBox: String): IntentStates()
    object Null: IntentStates()
}