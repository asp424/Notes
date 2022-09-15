package com.lm.notes.ui.bars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@Composable
fun DeleteBar(animScale: Float) {
    with(mainDep) {
        Box(
            Modifier
                .offset(width - 120.dp, 0.dp)
                .scale(animScale)
        ) {
            Icon(
                Icons.Rounded.Delete,
                null,
                modifier = Modifier.noRippleClickable { },
                tint = Color.White
            )
        }
    }
}