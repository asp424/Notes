package com.lm.notes.ui

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.R
import com.lm.notes.data.SPreferences
import com.lm.notes.data.remote_data.firebase.FBLoadStates
import com.lm.notes.data.remote_data.firebase.NoteModel
import com.lm.notes.di.compose.ComposeDependencies
import com.lm.notes.presentation.MainActivity
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.presentation.ViewModels
import com.lm.notes.utils.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

interface Screens {

    @Composable
    fun MainScreen()

    class Base @Inject constructor(
        private val composeDependencies: ComposeDependencies,
        private val viewModels: ViewModels,
        private val firebaseAuth: FirebaseAuth,
        private val sPreferences: SPreferences
    ) : Screens {

        @Composable
        override fun MainScreen() {
            LocalViewModelStoreOwner.current?.also { owner ->
                val notesViewModel = remember {
                    viewModels.viewModelProvider(owner)[NotesViewModel::class.java]
                }

                val lifecycle = LocalLifecycleOwner.current

                val notesList = notesViewModel.notesListState

                Column {
                    Box(Modifier.background(White)) {
                        with(composeDependencies.mainScreenDepsLocal()) {
                            TopAppBar(
                                Modifier
                                    .alpha(0.5f), backgroundColor = Blue
                            ) {
                            }

                            Canvas(
                                Modifier.offset(width - 55.dp - infoOffset, 28.dp)
                            ) { drawCircle(Red, infoOffset.value + 28f, Offset.Zero) }

                            Box(
                                Modifier.offset(width - 63.dp - infoOffset, 18.dp)
                            ) {
                                Icon(
                                    Icons.Sharp.Logout, null,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .noRippleClickable {
                                            firebaseAuth.signOut()
                                            if (firebaseAuth.currentUser?.uid == null) {
                                                sPreferences.saveIconUri(Uri.EMPTY)
                                                Uri.EMPTY.setIconUri
                                            }
                                        }, tint = White
                                )
                            }

                            Canvas(
                                Modifier.offset(width - 55.dp + infoOffset, 28.dp)
                            ) {
                                drawCircle(
                                    White, 46f,
                                    Offset.Zero
                                )
                            }

                            Box(
                                modifier = Modifier.offset(width - 70.dp + infoOffset, 13.dp)
                            ) {
                                (LocalContext.current as MainActivity).apply {
                                    AsyncImage(
                                        model = if (iconUri.toString() != "") iconUri else R.drawable.face,
                                        contentDescription = null,
                                        placeholder = painterResource(id = R.drawable.face),
                                        modifier = Modifier
                                            .size(if (!progressVisibility) 30.dp else 0.dp)
                                            .noRippleClickable {
                                                if (!firebaseAuth.isAuth) {
                                                    true.setProgressVisibility; startLoginActivity
                                                } else {
                                                    if (!infoVisibility) {
                                                        coroutine.launch {
                                                            if (infoVisibility) false.setInfoVisibility
                                                            else true.setInfoVisibility
                                                            delay(1000)
                                                            if (infoVisibility) false.setInfoVisibility
                                                            else true.setInfoVisibility
                                                        }
                                                    }
                                                }
                                            }
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop,
                                        onLoading = {
                                            if (iconUri.toString()
                                                    .isNotEmpty()
                                            ) true.setProgressVisibility
                                        }, onSuccess = { false.setProgressVisibility }
                                    )
                                }
                                if (progressVisibility) CircularProgressIndicator(
                                    modifier =
                                    Modifier
                                        .size(30.dp)
                                        .alpha(0.5f), color = Blue
                                )
                            }
                        }
                    }

                    when (notesList) {
                        is UiStates.Loading ->
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
                            { CircularProgressIndicator() }
                        is UiStates.Success ->
                            LazyColumn(content = {
                                notesList.listNotes.forEach {
                                    item(key = it.id, content = {
                                        Text(text = "${it.note}: ${it.timestamp}")
                                    })
                                }
                            })

                        is UiStates.Failure ->
                            Text(text = notesList.message)
                        else -> {}
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
                ) {
                    Column() {
                        Button(onClick = { notesViewModel.updateNotesList(lifecycle.lifecycleScope) }) {
                            Text(text = "update")
                        }
                        Button(onClick = { notesViewModel.saveNote("save") }) {
                            Text(text = "save")
                        }
                    }
                }
            }
        }
    }
}
