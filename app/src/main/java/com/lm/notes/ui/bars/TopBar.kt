package com.lm.notes.ui.bars

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.animScale
import com.lm.notes.utils.noRippleClickable

@Composable
fun TopBar() {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                val configuration = LocalConfiguration.current

                when (configuration.orientation ) {
                    Configuration.ORIENTATION_PORTRAIT -> {
                        TopAppBar(
                            backgroundColor = getMainColor, modifier = Modifier
                                .fillMaxWidth()
                                .noRippleClickable(remember { { false.setSettingsVisible } })
                        ) {
                            DefaultBar(animScale(getIsMainMode))
                            FullScreenBar()
                            DeleteBar(animScale(getIsDeleteMode))
                        }
                    }

                    else -> {
                        LandscapeBar()
                    }
                }
            }
        }
    }
}
