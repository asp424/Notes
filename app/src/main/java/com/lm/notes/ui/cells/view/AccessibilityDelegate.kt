package com.lm.notes.ui.cells.view

import android.view.View
import android.view.accessibility.AccessibilityEvent.TYPE_VIEW_LONG_CLICKED
import android.view.accessibility.AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED
import com.lm.notes.data.models.UiStates
import com.lm.notes.utils.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AccessibilityDelegate(
    private val editTextController: EditTextController,
    private val uiStates: UiStates
) : View.AccessibilityDelegate() {

    override fun sendAccessibilityEvent(host: View, eventType: Int) {
        super.sendAccessibilityEvent(host, eventType)
        with(uiStates) {
            with(editTextController) {
                eventType.log
                if (eventType == TYPE_VIEW_LONG_CLICKED) {
                    setFormatMode(); setAllButtonsWhite()
                }
                if (eventType == 1) job.cancel()
                if (eventType == TYPE_VIEW_TEXT_SELECTION_CHANGED && getIsFormatMode) {
                    job.cancel()
                    job = CoroutineScope(Dispatchers.Main).launch {
                       // delay(100)
                        if (editText.hasSelection()) {
                            saveSelection(); setButtonColors()
                        } else{
                            hideFormatPanel()
                            false.setIsSelected
                        }
                    }
                }
            }
        }
    }

    private var job: Job = Job()
}

