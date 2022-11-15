package com.lm.notes.ui.cells.view.app_widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import com.lm.notes.R
import com.lm.notes.core.appComponent
import com.lm.notes.utils.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class NoteAppWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        context?.appComponent?.noteAppWidgetController()?.apply {
            CoroutineScope(IO).launch {
                setUpTextToWidget(appWidgetIds?.toList()?.get(0).toString()).apply {
                    setOnClickPendingIntent(R.id.note_text,
                        PendingIntent.getBroadcast(context, 0, Intent(
                            context, NoteAppWidget::class.java
                        ).apply { action = appWidgetIds?.toList()?.get(0).toString() }, 0)
                    )
                    appWidgetManager?.updateAppWidget(appWidgetIds, this)
                }
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
         context?.appComponent?.noteAppWidgetController()?.apply {
            intent?.action?.let { act ->
                if (act.any { it.isDigit() }) CoroutineScope(IO).launch {
                    setStrikeThroughSpan(act)
                }
            }
        }
    }
}
