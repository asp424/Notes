package com.lm.notes.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.lm.notes.R
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.presentation.MainActivity
import com.lm.notes.ui.cells.bars.BottomBar
import com.lm.notes.ui.cells.bars.FormatBar
import com.lm.notes.ui.cells.bars.TopBarFromOrientation
import com.lm.notes.ui.cells.NavHostAnim
import com.lm.notes.ui.cells.SettingsCard
import com.lm.notes.utils.backPressHandle
import kotlinx.coroutines.launch

@SuppressLint("ContextCastToActivity")
@Composable
fun MainDependencies.MainScreen() {
    val coroutine = rememberCoroutineScope()
    Image(
        painterResource(R.drawable.notebook_list), null,
        Modifier.fillMaxSize().alpha(0.5f), contentScale = Crop
    )

    Column {
        TopBarFromOrientation()
        NavHostAnim()
    }
    BottomBar()
    FormatBar()
    val mainActivity = LocalContext.current as MainActivity
    BackHandler(
        onBack = remember {
            { coroutine.launch { backPressHandle(notesViewModel, mainActivity) } }
        }
    )

    SettingsCard()
    notesViewModel.uiStates.NavControllerController()
}
