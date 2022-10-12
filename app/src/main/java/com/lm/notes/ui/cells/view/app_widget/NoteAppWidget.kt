package com.lm.notes.ui.cells.view.app_widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import com.lm.notes.core.noteWidgetComponent


class NoteAppWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        appWidgetManager?.updateAppWidget(
            appWidgetIds,
            context?.noteWidgetComponent?.noteAppWidgetController()?.remoteViews()
        )
    }
}
