package com.lm.notes.ui.cells.view

import android.os.Build
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UnderlineSpan
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi

class ColoredUnderlineSpan constructor(
    @ColorInt val underlineColor: Int,
    private val underlineThickness: Float,
) : UnderlineSpan() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun updateDrawState(textPaint: TextPaint) {
        textPaint.underlineColor = underlineColor
        textPaint.underlineThickness = underlineThickness
        textPaint.baselineShift
    }
}