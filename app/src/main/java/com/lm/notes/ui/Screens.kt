package com.lm.notes.ui

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Logout
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.R
import com.lm.notes.data.SPreferences
import com.lm.notes.di.compose.ComposeDependencies
import com.lm.notes.presentation.MainActivity
import com.lm.notes.presentation.MainViewModel
import com.lm.notes.presentation.ViewModels
import com.lm.notes.utils.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.cos
import kotlin.math.sin

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
                val mainViewModel = remember {
                    viewModels.viewModelProvider(owner)[MainViewModel::class.java]
                }

                Box(
                    Modifier
                        .fillMaxSize()
                        .background(White)
                ) {
                    with(composeDependencies.mainScreenDepsLocal()) {
                        Column(Modifier.fillMaxSize()) {
                            TopAppBar(
                                Modifier
                                    .alpha(0.5f)
                                    //.padding(bottom = infoHeight)
                                    , backgroundColor = Blue
                            ) {
                            }
                        }
                        Canvas(
                            Modifier.offset(width - 55.dp - infoHeightEnd, 28.dp)
                        ) {
                            drawCircle(
                                Red, 65f,
                                Offset.Zero
                            )
                        }
                        Box(
                            Modifier.offset(width - 65.dp - infoHeightEnd, 18.dp)
                        ) {
                            Icon(Icons.Sharp.Logout, null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .noRippleClickable {
                                        firebaseAuth.signOut()
                                        if (firebaseAuth.currentUser?.uid == null) {
                                            sPreferences.saveIconUri(Uri.EMPTY)
                                            Uri.EMPTY.setIconUri
                                        }
                                    }, tint = White)
                        }
                        Canvas(
                            Modifier
                                .offset(width - 55.dp, 28.dp)
                                .padding(start = infoHeightStart)
                        ) {
                            repeat(650) {
                                drawCircle(
                                    White, 10f,
                                    Offset(55 * sin(it * 0.01f), 55 * cos(it * 0.01f))
                                )
                            }
                            drawCircle(
                                White, 53f,
                                Offset.Zero
                            )
                            repeat(630) {
                                drawCircle(
                                    Gray, 2f,
                                    Offset(48f * sin(it * 0.01f), 48f * cos(it * 0.01f))
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .offset(width - 70.dp, 13.dp)
                                .padding(start = infoHeightStart)
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
                                                coroutine.launch {
                                                    if (infoVisibility) false.setInfoVisibility
                                                    else true.setInfoVisibility
                                                    delay(1000)
                                                    if (infoVisibility) false.setInfoVisibility
                                                    else true.setInfoVisibility
                                                }
                                            }
                                        }
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop,
                                    onLoading = { if (iconUri.toString() != "") true.setProgressVisibility },
                                    onSuccess = { false.setProgressVisibility }
                                )
                                if (progressVisibility) CircularProgressIndicator(
                                    modifier =
                                    Modifier
                                        .size(30.dp)
                                        .alpha(0.5f), color = Blue
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
