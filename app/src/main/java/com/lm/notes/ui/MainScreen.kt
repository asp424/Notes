package com.lm.notes.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.lm.notes.R

@Composable
fun MainScreen() {
    Image(
        painter = painterResource(id = R.drawable.notebook_list), contentDescription = null,
        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop
    )

    Column {
        TopBar()
        MainColumn()
    }
    BottomBar()
}
