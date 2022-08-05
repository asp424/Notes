package com.lm.notes.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.End
import androidx.compose.foundation.layout.Arrangement.Start
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lm.notes.ui.theme.bar

@Composable
fun TopBar(isAuthIconVisibility: Boolean) {
    Box(Modifier.background(Color.White)) {
        Box(
            Modifier
                .height(60.dp)
                .fillMaxWidth()
                .background(bar)
        )
        DefaultBar(
            animateFloatAsState(
                if (isAuthIconVisibility) 1f else 0f, tween(350)
            ).value
        )
        Box(
            contentAlignment = Alignment.Center
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .scale(
                        animateFloatAsState(
                            if (isAuthIconVisibility) 0f else 1f, tween(350)
                        ).value
                    )
                    .padding(end = 20.dp, top = 15.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = End
            ) {
                FullScreenBar()
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp, top = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Start
            ) {
                FormatBar(isAuthIconVisibility)
            }
        }
    }
}
