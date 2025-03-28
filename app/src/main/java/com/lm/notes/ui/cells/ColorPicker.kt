package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker
import com.godaddy.android.colorpicker.toColorInt
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.core.SpanType
import com.lm.notes.utils.animDp

@Composable
fun ColorPickers(list: List<SpanType>) {
    with(mainDep) {
        with(nVM) {
            with(uiStates) {
                with(editTextController) {
                    list.forEach { type ->
                        with(type.getColorPicker()) {
                            val y = animDp(this, height - 200.dp, height)
                            val yIcon = animDp(this, height - 210.dp, height)
                            Box(Modifier, Center) {
                                HarmonyColorPicker(
                                    Modifier
                                        .size(150.dp)
                                        .offset(0.dp, y), ColorHarmonyMode.SHADES,
                                    onColorChanged = { c ->
                                        c.toColorInt().also { type.getType(it) }
                                    }
                                )
                                Icon(
                                    type.getIcon, null,
                                    Modifier.offset(0.dp, yIcon).size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

