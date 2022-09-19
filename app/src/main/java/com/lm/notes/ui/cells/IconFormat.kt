package com.lm.notes.ui.cells

import android.text.style.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.view.SpanType
import com.lm.notes.utils.noRippleClickable

@Composable
fun IconFormat(source: ImageVector) {
    with(mainDep) {
        with(spansProvider) {
            with(uiStates) {
                with(editText) {
                    val click by derivedStateOf {
                        {
                            when (source) {
                                Icons.Rounded.FormatBold -> {
                                    if (isHaveSpans(StyleSpan::class.java, SpanType.Bold)) {
                                        removeSpan(SpanType.Bold)
                                        Black.setColorButtonBold
                                    } else SpanType.Bold.setSpan()
                                }

                                Icons.Rounded.FormatItalic -> {
                                    if (isHaveSpans(
                                            StyleSpan::class.java,
                                            SpanType.Italic
                                        )
                                    ) {
                                        removeSpan(SpanType.Italic)
                                        Black.setColorButtonItalic
                                    } else SpanType.Italic.setSpan()
                                }

                                Icons.Rounded.FormatUnderlined -> {
                                    if (isHaveSpans(
                                            UnderlineSpan::class.java,
                                            SpanType.Underlined
                                        )
                                    ) {
                                        editText.removeSpan(SpanType.Underlined)
                                        Black.setColorButtonUnderlined
                                    } else SpanType.Underlined.setSpan()
                                }

                                Icons.Rounded.FormatColorText -> {
                                    with(SpanType.Foreground(getColorButtonForeground.toArgb())) {
                                        if (
                                            isHaveSpans(ForegroundColorSpan::class.java, this)
                                        ) {
                                            removeSpan(this)
                                            Black.setColorButtonForeground
                                        } else {
                                            if (!getColorPickerForegroundIsShow) true.setColorPickerForegroundIsShow
                                            else false.setColorPickerForegroundIsShow
                                        }
                                    }
                                }

                                Icons.Rounded.FormatColorFill -> {
                                    with(SpanType.Background(getColorButtonBackground.toArgb()))
                                    {
                                        if (
                                            isHaveSpans(BackgroundColorSpan::class.java, this)
                                        ) {
                                            removeSpan(this)
                                            Black.setColorButtonBackground
                                        } else {
                                            if (!getColorPickerBackgroundIsShow) true.setColorPickerBackgroundIsShow
                                            else false.setColorPickerBackgroundIsShow
                                        }
                                    }
                                }

                                Icons.Rounded.FormatStrikethrough -> {
                                    if (isHaveSpans(
                                            StrikethroughSpan::class.java,
                                            SpanType.StrikeThrough
                                        )
                                    ) {
                                        removeSpan(SpanType.StrikeThrough)
                                        Black.setColorButtonStrikeThrough
                                    } else SpanType.StrikeThrough.setSpan()
                                }
                                Icons.Rounded.FormatClear -> removeAllSpans()
                            }
                        }
                    }

                    Box(modifier = Modifier.padding(start = 10.dp)) {
                        Icon(
                            source, null,
                            modifier = Modifier.noRippleClickable(click),
                            tint = when (source) {
                                Icons.Rounded.FormatColorFill -> getColorButtonBackground
                                Icons.Rounded.FormatColorText -> getColorButtonForeground
                                Icons.Rounded.FormatUnderlined -> getColorButtonUnderlined
                                Icons.Rounded.FormatBold -> getColorButtonBold
                                Icons.Rounded.FormatItalic -> getColorButtonItalic
                                Icons.Rounded.FormatStrikethrough -> getColorButtonStrikeThrough
                                else -> Black
                            }
                        )
                    }
                }
            }
        }
    }
}
