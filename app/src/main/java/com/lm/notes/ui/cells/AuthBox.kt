package com.lm.notes.ui.cells

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lm.notes.R
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.presentation.MainActivity
import com.lm.notes.utils.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("ContextCastToActivity", "UseOfNonLambdaOffsetOverload")
@Composable
fun MainDependencies.AuthBox(size: Dp) {
    (LocalContext.current as MainActivity).apply {
        var isError by remember { mutableStateOf(false) }
        val click = remember(notesViewModel) {
            {
                if (!firebaseAuth.isAuth) {
                    progressVisibility.value = true; isError = false; startLoginActivity
                } else with(authButtonMenuVisibility) {
                        if (!value) coroutine.launch {
                            value = !value; delay(1000); value = !value
                        }
                    }
                }
            }
        val icon = remember { R.drawable.face }

        Box(Modifier.offset(authButtonMenuOffsetY)
                .iconVisibility(notesViewModel.uiStates.getIsMainMode)
        ) {
            Button(
                onClick = {}, modifier = Modifier
                    .size(size), colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {}
            with(progressVisibility) {
                AsyncImage(if (iconUri.value.toString() == "" || isError) icon
                else iconUri.value, null, Modifier.size(if (!value) size else 0.dp
                ).noRippleClickable(click).clip(CircleShape), painterResource(icon),
                    contentScale = ContentScale.Crop,
                    onLoading = { if (iconUri.toString().isNotEmpty()) value = true },
                    onSuccess = { progressVisibility.value = false },
                    onError = { isError = true; value = false })
                if (value) CircularProgressIndicator(Modifier.size(size).alpha(0.5f),
                    notesViewModel.uiStates.getSecondColor
                )
            }
        }
    }
}
