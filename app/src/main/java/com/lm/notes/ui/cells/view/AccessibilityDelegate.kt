package com.lm.notes.ui.cells.view

import android.view.View
import android.view.accessibility.AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED
import com.lm.notes.data.models.UiStates
import javax.inject.Inject

class AccessibilityDelegate @Inject constructor(
    private val spansProvider: SpansProvider,
    private val uiStates: UiStates
) : View.AccessibilityDelegate() {
    override fun sendAccessibilityEvent(host: View, eventType: Int) {
        super.sendAccessibilityEvent(host, eventType)
        with(uiStates) {
            if (eventType == 2) true.setLongClickState
            if (eventType == TYPE_VIEW_TEXT_SELECTION_CHANGED && getLongClickState) {
                setBlack()
                with(spansProvider) {
                    listClasses.forEach { setAutoColor(it, listSpans(it.clazz)) }
                    setFormatMode()
                }
            }
        }
    }
}
