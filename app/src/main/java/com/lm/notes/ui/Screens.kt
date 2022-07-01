package com.lm.notes.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import coil.compose.AsyncImage
import com.lm.notes.R
import com.lm.notes.di.compose.ComposeDependencies
import com.lm.notes.presentation.MainActivity
import com.lm.notes.presentation.MainViewModel
import com.lm.notes.presentation.ViewModels
import com.lm.notes.utils.noRippleClickable
import javax.inject.Inject
import kotlin.math.cos
import kotlin.math.sin

interface Screens {

    @Composable
    fun MainScreen()

    class Base @Inject constructor(
        private val composeDependencies: ComposeDependencies,
        private val viewModels: ViewModels
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
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                            TopAppBar(Modifier.alpha(0.5f), backgroundColor = Blue) {

                            }
                        }

                        Canvas(Modifier.offset(width - 55.dp, 60.dp)) {
                            repeat(650) {
                                drawCircle(
                                    White, 10f,
                                    Offset(scaleX * sin(it * 0.01f), scaleX * cos(it * 0.01f))
                                )
                            }
                            drawCircle(
                                White, 75f,
                                Offset.Zero
                            )
                            repeat(630) {
                                drawCircle(
                                    Gray, 2f,
                                    Offset(70f * sin(it * 0.01f), 70f * cos(it * 0.01f))
                                )
                            }
                        }
                        Box(modifier = Modifier.offset(width - 78.dp, 37.dp)) {
                            (LocalContext.current as MainActivity).apply {
                                AsyncImage(
                                    model = if (iconUri.toString() != "") iconUri else R.drawable.face,
                                    contentDescription = null,
                                    placeholder = painterResource(id = R.drawable.face),
                                    modifier = Modifier
                                        .size(if (!progressVisibility) 46.dp else 0.dp)
                                        .noRippleClickable {
                                            if (!firebaseAuth.isAuth){
                                                true.setProgressVisibility; startLoginActivity
                                            }
                                        }.clip(CircleShape),
                                    contentScale = ContentScale.Crop,
                                    onLoading = { true.setProgressVisibility },
                                    onSuccess = { false.setProgressVisibility }
                                )
                                if (progressVisibility) CircularProgressIndicator(modifier =
                                Modifier.size(46.dp).alpha(0.5f), color = Blue)
                            }
                        }
                       // Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        //    if (progressVisibility) CircularProgressIndicator()
                        //}
                    }
                }
            }
        }
    }
}
