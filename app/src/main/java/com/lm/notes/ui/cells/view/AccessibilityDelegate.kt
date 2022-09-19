package com.lm.notes.ui.cells.view

import android.os.IBinder
import android.text.style.*
import android.view.View
import android.view.accessibility.AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED
import android.view.inputmethod.InputMethodManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import com.lm.notes.data.models.UiStates
import javax.inject.Inject

class AccessibilityDelegate @Inject constructor(
    private val spansProvider: SpansProvider,
    private val inputMethodManager: InputMethodManager
) : View.AccessibilityDelegate() {

    override fun sendAccessibilityEvent(host: View?, eventType: Int) {
        super.sendAccessibilityEvent(host, eventType)
        with(spansProvider) {
            with(uiStates) {
                if (eventType == 2) true.setLongClickState
                if (eventType == TYPE_VIEW_TEXT_SELECTION_CHANGED && getLongClickState) {
                    Black.setAllButtonsColor
                    with(editText) {

                        with(listSpans(BackgroundColorSpan::class.java)) {
                            if (isNotEmpty())
                                get(0).backgroundColor.getColor.setColorButtonBackground
                        }
                        with(listSpans(ForegroundColorSpan::class.java)) {
                            if (isNotEmpty())
                                get(0).foregroundColor.getColor.setColorButtonForeground
                        }

                        if (listSpans(UnderlineSpan::class.java).isNotEmpty())
                            Green.setColorButtonUnderlined

                        if (filteredByStyle(SpanType.Bold, listSpans(StyleSpan::class.java))
                                .isNotEmpty()
                        ) Green.setColorButtonBold

                        if (filteredByStyle(
                                SpanType.Italic, listSpans(StyleSpan::class.java)
                            ).isNotEmpty()
                        ) Green.setColorButtonItalic

                        if (listSpans(StrikethroughSpan::class.java).isNotEmpty()
                        ) Green.setColorButtonStrikeThrough

                        windowToken?.hideKeyboard
                        showSoftInputOnFocus = false
                    }
                }
            }
        }
    }

    private val Spans.getSpanClass
        get() = when (this) {
            Spans.UNDERLINED -> UnderlineSpan::class.java
            Spans.BACKGROUND -> BackgroundColorSpan::class.java
            Spans.FOREGROUND -> ForegroundColorSpan::class.java
            Spans.STRIKE -> StrikethroughSpan::class.java
            Spans.BOLD -> StyleSpan::class.java
            Spans.ITALIC -> StyleSpan::class.java
        }

    private val listClasses get() = listOf(
        UnderlineSpan::class.java,
        BackgroundColorSpan::class.java,
        ForegroundColorSpan::class.java,
        StrikethroughSpan::class.java,
        StyleSpan::class.java,
        StyleSpan::class.java
        )

    private val IBinder.hideKeyboard
        get() = inputMethodManager.hideSoftInputFromWindow(this, 0)

    private val Int.getColor get() = Color(this)
}
