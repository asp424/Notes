package com.lm.notes.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.lm.notes.R
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    with(mainDep) {

        var isNotFullScreenNote by remember { mutableStateOf(true) }

        Image(
            painter = painterResource(id = R.drawable.notebook_list), null,
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop
        )

        Column {

            TopBar(isNotFullScreenNote)

            NavController {
                coroutine.launch {
                    delay(100)
                    isNotFullScreenNote = it
                }
            }
        }

        BottomBar(isNotFullScreenNote)

        (LocalContext.current as MainActivity).apply {
            BackHandler {
                if (navController.currentDestination?.route == "mainList") finish()
                else navController.navigate("mainList")
            }
        }
    }
}
