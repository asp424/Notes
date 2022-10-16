package com.lm.notes.data.local_data

import android.app.Application
import android.content.Context
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.lm.notes.data.models.NoteModel
import com.lm.notes.presentation.MainActivity
import com.lm.notes.ui.cells.view.EditTextController
import java.io.File
import java.net.URLConnection
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

interface FilesProvider {

    fun saveText(date: Long, text: String, shareType: ShareType): File

    fun takeAllFiles(): MutableList<File>

    fun readFile(name: String): String

    fun deleteFile(name: String): Boolean

    fun deleteAllFiles()

    fun NoteModel.shareAsFile(
        shareType: ShareType, header: String, activity: MainActivity
    )

    fun shareAsText(text: String, activity: MainActivity)

    class Base @Inject constructor(
        private val context: Application,
        private val filesDir: File,
        private val editTextController: EditTextController
    ) : FilesProvider {

        override fun saveText(date: Long, text: String, shareType: ShareType) =
            date.newFileName(shareType).file.apply {
                runCatching { createNewFile() }.onSuccess { writeText(text) }
            }

        override fun takeAllFiles() = absPath.walkTopDown().toMutableList()
            .apply { if (isNotEmpty()) removeAt(0) }

        override fun readFile(name: String) = name.file.readText()

        override fun deleteFile(name: String) = name.file.delete()

        override fun deleteAllFiles() = absPath.walkTopDown().forEach { it.delete() }

        override fun NoteModel.shareAsFile(
            shareType: ShareType, header: String, activity: MainActivity
        ) {
            with(
                saveText(timestampChangeState.value, with(Pair(header, text)) {
                        if (shareType == ShareType.AsHtml) asHtml else asText }, shareType
                )
            ) {
                activity.getIntentBuilder. setStream(provider)
                    .setType(URLConnection.guessContentTypeFromName(name)).startChooser()
            }
        }

        override fun shareAsText(text: String, activity: MainActivity) =
            activity.getIntentBuilder.setType("text/plain").setHtmlText(text).startChooser()

        private val String.file get() = File(filesDir, this)

        private val File.provider get() = FileProvider.getUriForFile(context, authority, this)

        private val pattern by lazy { "yyyy-MM-dd HH:mm:ss" }

        private val absPath get() = File(filesDir.absolutePath)

        private val authority by lazy { "com.lm.notes.fileProvider" }

        private fun Long.newFileName(shareType: ShareType) =
            "${SimpleDateFormat(pattern, Locale.getDefault()).format(this)}${shareType.type}"

        private val Pair<String, String>.asText
            get() = "${first}\n\n${editTextController.fromHtml(second)}"

        private val Pair<String, String>.asHtml get() = "${first}\n\n${second}"

        private val Context.getIntentBuilder get() =
            ShareCompat.IntentBuilder(this)
    }
}

typealias IntentBorn = () -> ShareCompat.IntentBuilder