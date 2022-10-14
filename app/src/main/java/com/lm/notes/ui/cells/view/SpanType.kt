package com.lm.notes.ui.cells.view

import android.graphics.Typeface
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatColorFill
import androidx.compose.material.icons.rounded.FormatColorText
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.toArgb
import com.lm.notes.utils.log

sealed class SpanType {
    class Background(var color: Int = 0) : SpanType()
    class Foreground(var color: Int = 0) : SpanType()
    object Bold : SpanType()
    object Italic : SpanType()
    object Underlined : SpanType()
    object StrikeThrough : SpanType()
    class Relative(var scale: Float = 0f) : SpanType()
    object Url : SpanType()

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
        }
}
