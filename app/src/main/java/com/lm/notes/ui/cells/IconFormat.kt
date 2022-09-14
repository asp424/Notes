package com.lm.notes.ui.cells

import android.graphics.Color
import android.graphics.Typeface
import android.text.style.BackgroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatBold
import androidx.compose.material.icons.rounded.FormatColorText
import androidx.compose.material.icons.rounded.FormatItalic
import androidx.compose.material.icons.rounded.FormatUnderlined
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.log
import com.lm.notes.utils.noRippleClickable
@Composable
fun IconFormat(source: ImageVector) {
    with(mainDep) {
        with(editTextProvider) {
            val click by derivedStateOf {
                {
                    when (source) {
                        Icons.Rounded.FormatBold -> setSpan(StyleSpan(Typeface.BOLD))

                        Icons.Rounded.FormatItalic -> setSpan(StyleSpan(Typeface.ITALIC))

                        Icons.Rounded.FormatUnderlined -> setSpan(UnderlineSpan())

                        Icons.Rounded.FormatColorText -> setSpan(BackgroundColorSpan(Color.YELLOW))
                    }
                }
            }

            Box(modifier = Modifier.padding(start = 10.dp)) {
                Icon(
                    source, null,
                    modifier = Modifier.noRippleClickable(click),
                    tint = Black
                )
            }
        }
    }
}