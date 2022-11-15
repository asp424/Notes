package com.lm.notes.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.text.toHtml
import androidx.core.text.toSpanned
import com.lm.notes.R
import com.lm.notes.core.IntentStates
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.MainActivity
import com.lm.notes.ui.bars.BottomBar
import com.lm.notes.ui.bars.FormatBar
import com.lm.notes.ui.bars.TopBar
import com.lm.notes.ui.cells.NavHost
import com.lm.notes.ui.cells.SettingsCard
import com.lm.notes.utils.backPressHandle
import kotlinx.coroutines.launch

@Composable
fun MainScreen(intentStates: IntentStates) {
    Image(
        painter = painterResource(id = R.drawable.notebook_list), null,
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.5f), contentScale = ContentScale.Crop
    )

    with(mainDep) {
        with(notesViewModel) {
            val actionOnNewIntent: (IntentStates) -> Unit = remember {
                {
                    navController.navigate("fullScreenNote") {
                        popUpTo("mainList")
                    }
                    addNewNote(coroutine) {
                        when (it) {
                            is IntentStates.SendPlain ->
                                editTextController.setNewText(it.text.toSpanned().toHtml())

                            is IntentStates.ViewPlain ->
                                filesProvider.readTextFileFromDeviceAndSetToEditText(it.uri)

                            else -> Unit
                        }
                        checkForEmptyText()
                    }
                }
            }
            Column {
                TopBar()
                NavHost()
            }
            BottomBar()
            FormatBar()
            val mainActivity = LocalContext.current as MainActivity
            BackHandler(
                onBack = remember {
                    {
                        coroutine.launch {
                            backPressHandle(navController, notesViewModel, mainActivity)
                        }
                    }
                }
            )

            LaunchedEffect(intentStates) {
                when (intentStates) {
                    IntentStates.Null -> Unit
                    else -> {
                        editTextController.createEditText()
                        actionOnNewIntent(intentStates)
                    }
                }
            }
        }
    }
    SettingsCard()
}
