package com.lm.notes.ui.cells

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker
import com.godaddy.android.colorpicker.toColorInt
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.animScale

@Composable
fun SettingsCard() {
    with(mainDep) {
        with(notesViewModel.uiStates) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .scale(animScale(getSettingsVisible, 700))
                        .fillMaxSize()
                        .padding(top = 56.dp), colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ), border = BorderStroke(3.dp, getMainColor)
                ) {
                    Column() {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            Text(text = "Main color")
                            HarmonyColorPicker(
                                Modifier
                                    .size(100.dp), ColorHarmonyMode.SHADES,
                                onColorChanged = { c ->
                                    Color(c.toColorInt()).setMainColor
                                    sPreferences.saveMainColor(c.toColorInt())
                                }
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            Text(text = "Second color")
                            HarmonyColorPicker(
                                Modifier
                                    .size(100.dp), ColorHarmonyMode.SHADES,
                                onColorChanged = { c ->
                                    Color(c.toColorInt()).setSecondColor
                                    sPreferences.saveSecondColor(c.toColorInt())
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}