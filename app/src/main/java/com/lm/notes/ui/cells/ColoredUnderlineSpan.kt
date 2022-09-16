package com.lm.notes.ui.cells

import android.os.Build
import android.text.TextPaint
import android.text.style.CharacterStyle
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi

class ColoredUnderlineSpan constructor(
    @ColorInt val underlineColor: Int,
    private val underlineThickness: Float,
) : CharacterStyle() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun updateDrawState(textPaint: TextPaint) {
        textPaint.underlineColor = underlineColor
        textPaint.underlineThickness = underlineThickness
    }
}