package com.lm.notes.core

import android.app.Application
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import javax.inject.Inject

interface Shortcuts {

    fun pushShortcut(
        id: String, label: String, longLabel: String, intent: Intent, logo: Int
    )

    fun disableShortcut(id: String)

    class Base @Inject constructor(
        private val application: Application
    ) : Shortcuts {

        override fun pushShortcut(
            id: String, label: String, longLabel: String, intent: Intent, logo: Int
        ) {
            ShortcutInfoCompat.Builder(application, id)
                .setShortLabel(label)
                .setLongLabel(longLabel)
                .setIcon(IconCompat.createWithResource(application, logo))
                .setIntent(intent)
                .build().also { shortcut ->
                    ShortcutManagerCompat.pushDynamicShortcut(application, shortcut)
                }
        }

        override fun disableShortcut(id: String) {
            ShortcutManagerCompat.disableShortcuts(application, listOf(id), "")
        }
    }
}