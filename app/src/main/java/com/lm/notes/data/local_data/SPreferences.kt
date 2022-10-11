package com.lm.notes.data.local_data

import android.content.SharedPreferences
import android.net.Uri
import androidx.core.net.toUri
import com.lm.notes.ui.theme.main
import javax.inject.Inject

interface SPreferences {

    fun saveIconUri(uri: Uri?)

    fun readIconUri(): Uri?

    fun saveMainColor(color: Int)

    fun readMainColor(): Int

    class Base @Inject constructor(
        private val sharedPreferences: SharedPreferences,
    ) : SPreferences {

        override fun saveIconUri(uri: Uri?) {
            sharedPreferences.edit()
                .putString(Uri.EMPTY.toString(), uri.toString()).apply()
        }

        override fun readIconUri() = sharedPreferences
            .getString(Uri.EMPTY.toString(), "")?.toUri()

        override fun saveMainColor(color: Int) {
            sharedPreferences.edit().putInt("color", color).apply()
        }

        override fun readMainColor() = sharedPreferences.getInt("color", (0xFF00BCD4).toInt())
    }
}