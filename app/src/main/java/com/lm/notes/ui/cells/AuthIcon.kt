package com.lm.notes.ui.cells

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lm.notes.R
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.MainActivity
import com.lm.notes.utils.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("ContextCastToActivity", "UseOfNonLambdaOffsetOverload")
@Composable
fun AuthIcon(animScale: Float) {
    with(mainDep) {
        (LocalContext.current as MainActivity).apply {

            val click = remember(notesViewModel) {
                {
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
            }
            val onLoading = remember(iconUri) {
                {
                    if (iconUri.toString().isNotEmpty()) progressVisibility.value = true
                }
            }

            var icon by remember { mutableIntStateOf(R.drawable.face) }

            var isError by remember { mutableStateOf(false) }

            var padding by remember { mutableStateOf(0.dp) }

            var shape by remember { mutableStateOf(CircleShape) }

            Box(
                modifier = Modifier
                    .offset(width - 200.dp + infoOffset.value, 0.dp)
                    .scale(animScale)
            ) {
                AsyncImage(model =
                if (iconUri.value.toString() != "") if (isError) icon else iconUri.value
                else {
                    shape = CircleShape
                    padding = 0.dp
                    R.drawable.face
                },
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.face),
                    modifier = Modifier
                        .size(if (!progressVisibility.value) 30.dp else 0.dp)
                        .noRippleClickable(click).padding(padding)
                        .clip(shape),
                    contentScale = ContentScale.Crop,
                    onLoading = { onLoading() },
                    onSuccess = { progressVisibility.value = false },
                    onError = {
                        icon = R.drawable.is_auth
                        isError = true
                        padding = 3.dp
                        shape = RoundedCornerShape(0.dp)
                        progressVisibility.value = false
                    })
                if (progressVisibility.value) CircularProgressIndicator(
                    modifier = Modifier
                        .size(30.dp)
                        .alpha(0.5f), color = Color.Blue
                )
            }
        }
    }
}
