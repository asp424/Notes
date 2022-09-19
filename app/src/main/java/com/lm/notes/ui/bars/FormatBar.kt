package com.lm.notes.ui.bars

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker
import com.godaddy.android.colorpicker.toColorInt
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.IconFormat
import com.lm.notes.ui.cells.view.SpanType
import com.lm.notes.utils.animDp

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FormatBar(isFormatMode: Boolean) {
    with(mainDep) {
        with(spansProvider) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(
                        0.dp, animDp(
                            isFormatMode, first = height - 45.dp, second = height, 100
                        )
                    )
                    .height(45.dp)
                    .padding(1.dp),
                border = BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(20.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Row {
                            listOf(
                                Icons.Rounded.FormatBold,
                                Icons.Rounded.FormatItalic,
                                Icons.Rounded.FormatUnderlined,
                                Icons.Rounded.FormatStrikethrough,
                                Icons.Rounded.FormatColorText,
                                Icons.Rounded.FormatColorFill,
                                Icons.Rounded.FormatClear,
                            ).forEach { IconFormat(it) }
                        }
                    }
                }
            }
            with(uiStates) {
                Box(
                    Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Box(contentAlignment = Alignment.Center) {
                            HarmonyColorPicker(
                                modifier = Modifier
                                    .size(150.dp)
                                    .offset(
                                        0.dp,
                                        animDp(
                                            getColorPickerForegroundIsShow,
                                            height - 200.dp,
                                            height,
                                            100
                                        )
                                    ),
                                onColorChanged = { hsvColor ->
                                    hsvColor.toColorInt()
                                        .also { SpanType.Foreground(it).setSpan() }
                                }, harmonyMode = ColorHarmonyMode.SHADES
                            )
                            Icon(
                                Icons.Rounded.FormatColorText, null, modifier =
                                Modifier
                                    .offset(
                                        0.dp,
                                        animDp(
                                            getColorPickerForegroundIsShow,
                                            height - 210.dp,
                                            height,
                                            100
                                        )
                                    )
                                    .size(20.dp)
                            )
                        }
                        Box(contentAlignment = Alignment.Center) {
                            HarmonyColorPicker(
                                modifier = Modifier
                                    .size(150.dp)
                                    .offset(
                                        0.dp,
                                        animDp(
                                            getColorPickerBackgroundIsShow,
                                            height - 200.dp,
                                            height,
                                            100
                                        )
                                    ),
                                onColorChanged = { hsvColor ->
                                    hsvColor.toColorInt()
                                        .also { SpanType.Background(it).setSpan() }
                                }, harmonyMode = ColorHarmonyMode.SHADES
                            )
                            Icon(
                                Icons.Rounded.FormatColorFill, null, modifier =
                                Modifier
                                    .offset(
                                        0.dp,
                                        animDp(
                                            getColorPickerBackgroundIsShow,
                                            height - 210.dp,
                                            height,
                                            100
                                        )
                                    )
                                    .size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
