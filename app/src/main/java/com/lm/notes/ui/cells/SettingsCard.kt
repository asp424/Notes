package com.lm.notes.ui.cells

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker
import com.godaddy.android.colorpicker.toColorInt
import com.lm.notes.R
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.animScale
import com.lm.notes.utils.noRippleClickable

@Composable
fun SettingsCard() {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                with(editTextController) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .scale(animScale(getSettingsVisible))
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
                                    Text(text = stringResource(R.string.main_color))
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
                                    Text(text = stringResource(R.string.second_color))
                                    HarmonyColorPicker(
                                        Modifier
                                            .size(100.dp), ColorHarmonyMode.SHADES,
                                        onColorChanged = { c ->
                                            Color(c.toColorInt()).setSecondColor
                                            sPreferences.saveSecondColor(c.toColorInt())
                                        }
                                    )
                                }

                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 20.dp, end = 55.dp, top = 40.dp)
                                ) {
                                    Text(text = stringResource(R.string.change_keyboard))
                                    Icon(Icons.Default.Keyboard, null,
                                        modifier = Modifier
                                        .noRippleClickable(
                                            remember { { showDialogChangeKeyboard() } }
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}