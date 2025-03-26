package com.lm.notes.ui.cells

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import com.lm.notes.R

@Composable
fun BackgroundImage() = Image(
    painterResource(R.drawable.notebook_list), null,
    Modifier.fillMaxSize().alpha(0.5f), contentScale = Crop
)
