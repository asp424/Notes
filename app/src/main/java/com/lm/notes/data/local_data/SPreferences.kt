package com.lm.notes.data.local_data

import android.content.SharedPreferences
import android.net.Uri
import androidx.core.net.toUri
import javax.inject.Inject

interface SPreferences {

    fun saveIconUri(uri: Uri?)

    fun readIconUri(): Uri?

    fun saveMainColor(color: Int)

    fun readMainColor(): Int

    fun saveSecondColor(color: Int)

    fun readSecondColor(): Int

    fun setNoteId(id: String)

    fun getNoteId(id: String): String

    fun setPinnedNoteId(noteId: String)

    fun getPinnedNoteId(): String

    fun getSortState(): Int

    fun setSortState(state: Int)

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
            sharedPreferences.edit().putInt("mainColor", color).apply()
        }

        override fun readMainColor() = sharedPreferences.getInt("mainColor", (0xFF00BCD4).toInt())

        override fun saveSecondColor(color: Int) {
            sharedPreferences.edit().putInt("secondColor", color).apply()
        }

        override fun readSecondColor() =
            sharedPreferences.getInt("secondColor", (0xFFFFFFFF).toInt())

        override fun setNoteId(id: String) {
            sharedPreferences.edit().putString(id, getPinnedNoteId()).apply()
        }

        override fun getNoteId(id: String) = sharedPreferences.getString(id, "")?:""

        override fun setPinnedNoteId(noteId: String) {
            sharedPreferences.edit().putString("pinned", noteId).apply()
        }

        override fun getPinnedNoteId() =
            sharedPreferences.getString("pinned", "")?:""

        override fun getSortState(): Int = sharedPreferences.getInt("sort", 0)

        override fun setSortState(state: Int) =
            sharedPreferences.edit().putInt("sort", state).apply()

    }
}