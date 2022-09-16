package com.lm.notes.ui.cells

import android.graphics.Color
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun IconFormat(source: ImageVector) {
    with(mainDep) {
        with(editTextProvider) {

            val click by derivedStateOf {
                {
                    when (source) {
                        Icons.Rounded.FormatBold -> {}
                            //setSpan(StyleSpan(Typeface.BOLD))

                        Icons.Rounded.FormatItalic ->{}
                           // setSpan(StyleSpan(Typeface.ITALIC))

                        Icons.Rounded.FormatUnderlined -> if (
                            isHaveSpans(ColoredUnderlineSpan::class.java)
                        ) { removeUnderlinedSpan()
                            colorButtonUnderlined = Black
                        } else {
                            if (!colorPickerUnderlinedIsShow) showColorPickerUnderlined()
                            else hideColorPickerUnderlined()
                        }

                        Icons.Rounded.FormatColorText -> {
                                if (
                                    isHaveSpans(ForegroundColorSpan::class.java)
                                ) { removeForegroundSpan()
                                    colorButtonForeground = Black
                                } else {
                                    if (!colorPickerForegroundIsShow) showColorPickerForeground()
                                    else hideColorPickerForeground()
                                }
                            }

                        Icons.Rounded.FormatColorFill -> {
                                if (
                                    isHaveSpans(BackgroundColorSpan::class.java)
                                ) { removeBackgroundSpan()
                                    colorButtonBackground = Black
                                } else {
                                    if (!colorPickerBackgroundIsShow) showColorPickerBackground()
                                    else hideColorPickerBackground()
                                }
                        }
                    }
                }
            }

            Box(modifier = Modifier.padding(start = 10.dp)) {
                Icon(
                    source, null,
                    modifier = Modifier.noRippleClickable(click),
                    tint = when (source) {
                        Icons.Rounded.FormatColorFill -> colorButtonBackground
                        Icons.Rounded.FormatColorText -> colorButtonForeground
                        Icons.Rounded.FormatUnderlined -> colorButtonUnderlined
                        else -> Black
                    }
                )
            }
        }
    }
}
