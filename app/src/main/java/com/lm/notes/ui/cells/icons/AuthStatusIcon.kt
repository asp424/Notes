package com.lm.notes.ui.cells.icons

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.presentation.MainActivity
import com.lm.notes.utils.getIconUri
import com.lm.notes.utils.getIsAuth
import com.lm.notes.utils.modifiers.noRippleClickable
import com.lm.notes.utils.setIconUri
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("ContextCastToActivity")
@Composable
fun MainDependencies.AuthStatusIcon(size: Dp) {
    LaunchedEffect(size) {
        sPreferences.readIconUri()?.setIconUri
    }
    val coroutine = rememberCoroutineScope()
    val mainActivity = LocalContext.current as MainActivity
    with(nVM.uiStates) {
        val click = remember(nVM) {
            {
                if (!getIsAuth) {
                    progressVisibility.value = true
                    mainActivity.startLoginActivity
                } else with(authButtonMenuVisibility) {
                    if (!value) coroutine.launch {
                        value = !value; delay(1000); value = !value
                    }
                }
            }
        }
        if (getIsAuth)
            AsyncImage(getIconUri, null,
                Modifier
                    .size(size)
                    .noRippleClickable(click)
                    .clip(CircleShape),
                contentScale = Crop,
                onLoading = { progressVisibility.value = true },
                onSuccess = { progressVisibility.value = false },
                onError = { progressVisibility.value = false })
        else Icon(
            Icons.Outlined.AccountCircle, null, Modifier
                .size(size)
                .noRippleClickable(click), tint = getMainColor
        )
    }
}
