package com.lm.notes.data.local_data

import android.annotation.SuppressLint
import android.app.Application
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.lm.notes.utils.log
import java.io.File
import java.net.URLConnection
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

interface FilesProvider {

    fun saveText(date: Long, text: String): File

    fun takeAllFiles(): MutableList<File>

    fun readFile(name: String): String

    fun deleteFile(name: String): Boolean

    fun deleteAllFiles()

    fun share(file: File)

    class Base @Inject constructor(
        private val context: Application,
        private val intentBuilder: ShareCompat.IntentBuilder,
        private val filesDir: File
    ) : FilesProvider {

        override fun saveText(date: Long, text: String) = date.newFileName.file.apply {
            runCatching { createNewFile() }.onSuccess { writeText(text) }
        }

        override fun takeAllFiles() = absPath.walkTopDown().toMutableList()
            .apply { if (isNotEmpty()) removeAt(0) }

        override fun readFile(name: String) = name.file.readText()

        override fun deleteFile(name: String) = name.file.delete()

        override fun deleteAllFiles() = absPath.walkTopDown().forEach { it.delete() }

        override fun share(file: File) = intentBuilder
            .setStream(file.provider).setType(URLConnection.guessContentTypeFromName(file.name))
            .startChooser()

        private val String.file get() = File(filesDir, this)

        private val File.provider get() = FileProvider.getUriForFile(context, authority, this)

        private val pattern by lazy { "yyyy-MM-dd HH:mm:ss" }

        private val absPath get() = File(filesDir.absolutePath)

        private val authority by lazy { "com.lm.notes.fileProvider" }

        private val Long.newFileName @SuppressLint("SimpleDateFormat")
        get() = "${SimpleDateFormat(pattern, Locale.getDefault()).format(this)}.txt"
    }
}