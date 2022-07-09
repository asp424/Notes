package com.lm.notes.ui

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Logout
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.R
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.di.compose.ComposeDependencies
import com.lm.notes.presentation.MainActivity
import com.lm.notes.utils.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

interface TopBar {

    @Composable
    fun Default()

    class Base @Inject constructor(
        private val composeDependencies: ComposeDependencies,
        private val firebaseAuth: FirebaseAuth,
        private val sPreferences: SPreferences
    ): TopBar{

        @Composable
        override fun Default() {
            composeDependencies.mainScreenDepsLocal() .apply {
                Box(Modifier.background(Color.White)) {
                    TopAppBar(
                        Modifier
                            .alpha(0.5f), backgroundColor = Color.Blue
                    ) {
                    }

                    Canvas(
                        Modifier.offset(width - 55.dp - infoOffset, 28.dp)
                    ) { drawCircle(Color.Red, infoOffset.value + 28f, Offset.Zero) }

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
                                }, tint = Color.White
                        )
                    }

                    Canvas(
                        Modifier.offset(width - 55.dp + infoOffset, 28.dp)
                    ) {
                        drawCircle(
                            Color.White, 46f,
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
                                    if (iconUri.toString().isNotEmpty()
                                    ) true.setProgressVisibility
                                }, onSuccess = { false.setProgressVisibility }
                            )
                        }
                        if (progressVisibility) CircularProgressIndicator(
                            modifier = Modifier
                                .size(30.dp)
                                .alpha(0.5f), color = Color.Blue
                        )
                    }
                }
            }
        }
    }
}