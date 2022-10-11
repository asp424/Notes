package com.lm.notes.ui.cells.view

import android.view.View
import android.view.accessibility.AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED
import com.lm.notes.data.models.UiStates
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccessibilityDelegate @Inject constructor(
    private val spansProvider: SpansProvider,
    private val uiStates: UiStates,
    private val coroutineDispatcher: CoroutineDispatcher
) : View.AccessibilityDelegate() {
    override fun sendAccessibilityEvent(host: View, eventType: Int) {
        super.sendAccessibilityEvent(host, eventType)
        with(uiStates) {
            if (eventType == 2) true.setIsFormatMode
            if(eventType == TYPE_VIEW_TEXT_SELECTION_CHANGED && getIsFormatMode) {
                CoroutineScope(coroutineDispatcher).launch {
                    with(spansProvider) {
                        setAllButtonsWhite()
                        setFormatMode()
                        if (spansProvider.isSelected()) {
                            setButtonColors()
                            saveSelection()
                        }
                    }
                }
            }
        }
    }
}
