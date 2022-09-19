package com.lm.notes.ui.cells.view

import android.graphics.Typeface
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.toArgb

sealed class SpanType {
    class ColoredUnderlined(var color: Int) : SpanType()
    class Background(var color: Int) : SpanType()
    class Foreground(var color: Int) : SpanType()
    object Bold : SpanType()
    object Italic : SpanType()
    object Underlined : SpanType()
    object StrikeThrough : SpanType()

    val getColor get() = when (this) {
            is ColoredUnderlined -> color
            is Background -> color
            is Foreground -> color
            is Underlined -> Green.toArgb()
            is Bold -> Green.toArgb()
            is Italic -> Green.toArgb()
            is StrikeThrough -> Green.toArgb()
        }

    val instance
        get() = when (this) {
            is ColoredUnderlined -> ColoredUnderlineSpan(getColor, 5f)
            is Background -> BackgroundColorSpan(getColor)
            is Foreground -> ForegroundColorSpan(getColor)
            is Bold -> StyleSpan(Typeface.BOLD)
            is Italic -> StyleSpan(Typeface.ITALIC)
            is Underlined -> UnderlineSpan()
            is StrikeThrough -> StrikethroughSpan()
        }

    val getTypeFace get() = when (this) {
        is Bold -> Typeface.BOLD
        is Italic -> Typeface.ITALIC
        else -> 0
    }

    val clazz get() = when(this){
        is ColoredUnderlined -> ColoredUnderlineSpan::class.java
        is Background -> BackgroundColorSpan::class.java
        is Foreground -> ForegroundColorSpan::class.java
        is Underlined -> UnderlineSpan::class.java
        is Bold -> StyleSpan::class.java
        is Italic -> StyleSpan::class.java
        is StrikeThrough -> StrikethroughSpan::class.java
    }
}

