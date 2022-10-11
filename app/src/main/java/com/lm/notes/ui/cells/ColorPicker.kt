package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatColorFill
import androidx.compose.material.icons.rounded.FormatColorText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker
import com.godaddy.android.colorpicker.toColorInt
import com.lm.notes.data.models.UiStates
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.view.SpanType
import com.lm.notes.ui.cells.view.SpansProvider
import com.lm.notes.utils.animDp

@Composable
fun ColorPickers(list: List<SpanType>) {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                with(spansProvider) {
                    list.forEach { type ->
                        with(getBoolean(type)) {

                            val y = animDp(this, height - 200.dp, height, 100)

                            val yIcon = animDp(this, height - 210.dp, height, 100)

                            Box(Modifier, Center) {

                                HarmonyColorPicker(
                                    Modifier
                                        .size(150.dp)
                                        .offset(0.dp, y), ColorHarmonyMode.SHADES,
                                    onColorChanged = { c ->
                                        c.toColorInt().also { getType(type, it) }
                                    }
                                )

                                Icon(
                                    type.getIcon, null,
                                    Modifier
                                        .offset(0.dp, yIcon)
                                        .size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun SpansProvider.getType(type: SpanType, color: Int) =
    if (type is SpanType.Background) SpanType.Background(color).setSpan()
    else SpanType.Foreground(color).setSpan()

private fun UiStates.getBoolean(type: SpanType) = if (type is SpanType.Background)
    getColorPickerBackgroundIsShow else getColorPickerForegroundIsShow

private val SpanType.getIcon
    get() = if (this is SpanType.Background)
        Icons.Rounded.FormatColorFill else Icons.Rounded.FormatColorText

