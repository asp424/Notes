package com.lm.notes.ui.cells

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.utils.animDp
import com.lm.notes.utils.modifiers.noRippleClickable
import com.lm.notes.utils.setIconUri
import com.lm.notes.utils.setIsAuth

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun MainDependencies.LogOutBox(size: Dp = 30.dp) = with(nVM.uiStates) {
    Box(
        Modifier
            .offset(-animDp(authButtonMenuVisibility.value, 20.dp, 0.dp), 0.dp)
            .iconVisibility(getIsMainMode)
    ) {
        Button(
            {}, Modifier.size(size),
            colors = ButtonDefaults.buttonColors(Red), contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Sharp.Logout, null, Modifier
                    .size(size)
                    .scale(0.6f)
                    .offset(x = 3.dp)
                    .noRippleClickable(
                        remember {
                            {
                                firebaseAuth.signOut()
                                if (firebaseAuth.currentUser?.uid == null) {
                                    sPreferences.saveIconUri(Uri.EMPTY)
                                    Uri.EMPTY.setIconUri
                                    false.setIsAuth
                                }
                            }
                        }), White
            )
        }

    }
}