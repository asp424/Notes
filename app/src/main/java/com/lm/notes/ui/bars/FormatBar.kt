package com.lm.notes.ui.bars

import android.annotation.SuppressLint
import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker
import com.godaddy.android.colorpicker.toColorInt
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.IconFormat
import com.lm.notes.utils.animDp

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FormatBar(isFormatMode: Boolean) {
    with(mainDep) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset(0.dp, animDp(
                    isFormatMode, first = height - 45.dp, second = height, 100)
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

                        IconFormat(Icons.Rounded.FormatBold)

                        IconFormat(Icons.Rounded.FormatItalic)

                        IconFormat(Icons.Rounded.FormatUnderlined)

                        IconFormat(Icons.Rounded.FormatColorText)

                        IconFormat(Icons.Rounded.FormatColorFill)
                    }
                }
            }
        }

            Box(Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center) {
                HarmonyColorPicker(
                    modifier = Modifier.size(150.dp)
                        .offset(0.dp, animDp(
                            editTextProvider.colorPickerIsShow,
                            first = height - 200.dp,
                            second = height,
                            100)
                        ),
                    onColorChanged = { hsvColor ->
                        hsvColor.toColorInt().also {
                            editTextProvider.setSpan(BackgroundColorSpan(it))
                            editTextProvider.colorButton = Color(it)
                        }
                    }, harmonyMode = ColorHarmonyMode.SHADES
                )
            }
        }
    }
