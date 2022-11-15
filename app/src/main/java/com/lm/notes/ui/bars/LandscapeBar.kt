package com.lm.notes.ui.bars

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.animScale
import com.lm.notes.utils.noRippleClickable

@Composable
fun LandscapeBar() {
    with(mainDep) {
        with(notesViewModel.uiStates) {
            val configuration = LocalConfiguration.current

            TopAppBar(
                backgroundColor = getMainColor, modifier = Modifier
                    .fillMaxWidth()
                    .height(if(configuration.orientation
                        == Configuration.ORIENTATION_PORTRAIT ) 45.dp else 15.dp)
                    .noRippleClickable(remember { { false.setSettingsVisible } })
            ) {
                DefaultBar(animScale(getIsMainMode))
                FullScreenBar()
                DeleteBar(animScale(getIsDeleteMode))
            }
        }
    }
}