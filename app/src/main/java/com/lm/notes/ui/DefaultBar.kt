package com.lm.notes.ui

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Logout
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lm.notes.R
import com.lm.notes.di.compose.MainDep
import com.lm.notes.presentation.MainActivity
import com.lm.notes.utils.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DefaultBar(iconAuthScale: Float) {
    with(MainDep.mainDep) {

        Canvas(
            Modifier
                .offset(width - 55.dp - infoOffset.value, 28.dp)
                .scale(iconAuthScale)
        ) { drawCircle(Color.Red, infoOffset.value.value + 20f, Offset.Zero) }

        Box(
            Modifier
                .offset(width - 63.dp - infoOffset.value, 18.dp)
                .scale(iconAuthScale)
        ) {
            Icon(
                Icons.Sharp.Logout, null, modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable {
                        firebaseAuth.signOut()
                        if (firebaseAuth.currentUser?.uid == null) {
                            sPreferences.saveIconUri(Uri.EMPTY)
                            iconUri.value = Uri.EMPTY
                        }
                    }, tint = Color.White
            )
        }

        Canvas(
            Modifier
                .offset(width - 55.dp + infoOffset.value, 28.dp)
                .scale(iconAuthScale)
        ) { drawCircle(Color.White, 15.dp.toPx(), Offset.Zero) }

        Box(
            modifier = Modifier
                .offset(width - 70.dp + infoOffset.value, 13.dp)
                .scale(iconAuthScale)
        ) {
            (LocalContext.current as MainActivity).apply {
                AsyncImage(model = if (iconUri.value.toString() != "") iconUri.value
                else R.drawable.face,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.face),
                    modifier = Modifier
                        .size(if (!progressVisibility.value) 30.dp else 0.dp)
                        .noRippleClickable {
                            if (!firebaseAuth.isAuth) {
                                progressVisibility.value = true; startLoginActivity
                            } else {
                                if (!infoVisibility.value) {
                                    coroutine.launch {
                                        infoVisibility.value = !infoVisibility.value
                                        delay(1000)
                                        infoVisibility.value = !infoVisibility.value
                                    }
                                }
                            }
                        }
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    onLoading = {
                        if (iconUri.toString()
                                .isNotEmpty()
                        ) progressVisibility.value = true
                    },
                    onSuccess = { progressVisibility.value = false })
            }
            if (progressVisibility.value) CircularProgressIndicator(
                modifier = Modifier
                    .size(30.dp)
                    .alpha(0.5f), color = Color.Blue
            )
        }
    }
}