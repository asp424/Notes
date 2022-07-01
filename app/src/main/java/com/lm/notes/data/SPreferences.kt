package com.lm.notes.data

import android.content.SharedPreferences
import android.net.Uri
import androidx.core.net.toUri
import javax.inject.Inject

interface SPreferences {

    fun saveIconUri(uri: Uri?)

    fun readIconUri(): Uri?

    class Base @Inject constructor(
        private val sharedPreferences: SharedPreferences,
    ) : SPreferences {

        override fun saveIconUri(uri: Uri?) {
            sharedPreferences
                .edit()
                .putString(Uri.EMPTY.toString(), uri.toString())
                .apply()
        }

        override fun readIconUri() = sharedPreferences
            .getString(Uri.EMPTY.toString(), "")?.toUri()
    }
}