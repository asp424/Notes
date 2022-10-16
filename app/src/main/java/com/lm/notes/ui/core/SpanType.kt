package com.lm.notes.ui.core

import android.graphics.Typeface
import android.text.style.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatColorFill
import androidx.compose.material.icons.rounded.FormatColorText
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb

sealed class SpanType {
    class Background(var color: Int = 0) : SpanType()
    class Foreground(var color: Int = 0) : SpanType()
    object Bold : SpanType()
    object Italic : SpanType()
    object Underlined : SpanType()
    object StrikeThrough : SpanType()
    class Relative(var scale: Float = 0f) : SpanType()
    object Url : SpanType()
    object Clear : SpanType()

    val getColor
        get() = when (this) {
            is Background -> color
            is Foreground -> color
            is Underlined -> Green.toArgb()
            is Bold -> Green.toArgb()
            is Italic -> Green.toArgb()
            is StrikeThrough -> Green.toArgb()
            is Relative -> Green.toArgb()
            is Url -> Green.toArgb()
            is Clear -> White.toArgb()
        }

    val listClasses by lazy {
        listOf(StrikeThrough, Underlined, Bold, Italic, Background(), Foreground(), Url)
    }

    val instance
        get() = when (this) {
            is Background -> BackgroundColorSpan(getColor)
            is Foreground -> ForegroundColorSpan(getColor)
            is Bold -> StyleSpan(Typeface.BOLD)
            is Italic -> StyleSpan(Typeface.ITALIC)
            is Underlined -> UnderlineSpan()
            is StrikeThrough -> StrikethroughSpan()
            is Relative -> RelativeSizeSpan(0f)
            is Url -> URLSpan("https://www.google.com/")
            is Clear -> StyleSpan(Typeface.NORMAL)
        }

    fun getTypeFace(type: SpanType) = when (type) {
        is Bold -> Typeface.BOLD
        is Italic -> Typeface.ITALIC
        else -> 0
    }

    val getIcon
        get() = if (this is Background)
            Icons.Rounded.FormatColorFill else Icons.Rounded.FormatColorText

    val clazz
        get() = when (this) {
            is Background -> BackgroundColorSpan::class.java
            is Foreground -> ForegroundColorSpan::class.java
            is Underlined -> UnderlineSpan::class.java
            is Bold -> StyleSpan::class.java
            is Italic -> StyleSpan::class.java
            is StrikeThrough -> StrikethroughSpan::class.java
            is Relative -> RelativeSizeSpan::class.java
            is Url -> URLSpan::class.java
            is Clear -> StyleSpan::class.java
        }
}
