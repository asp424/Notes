package com.lm.notes.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.lm.notes.R
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.MainActivity
import com.lm.notes.ui.bars.BottomBar
import com.lm.notes.ui.bars.FormatBar
import com.lm.notes.ui.bars.TopBar
import com.lm.notes.ui.cells.NavHost
import com.lm.notes.ui.cells.SettingsCard
import com.lm.notes.utils.backPressHandle
import com.lm.notes.utils.log

@Composable
fun MainScreen() {
    Image(
        painter = painterResource(id = R.drawable.notebook_list), null,
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.5f), contentScale = ContentScale.Crop
    )

    with(mainDep) {
        with(notesViewModel) {
            noteModelFullScreen.collectAsState().value.also { noteModel ->
                Column {
                    TopBar()
                    NavHost(noteModel)
                }
                BottomBar()
                FormatBar()
                (LocalContext.current as MainActivity).apply {
                    BackHandler {
                        backPressHandle(navController, notesViewModel, noteModel, this)
                    }
                }
                SettingsCard()
            }
        }
    }
}
