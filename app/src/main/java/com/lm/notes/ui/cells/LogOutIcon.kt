package com.lm.notes.ui.cells

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.utils.noRippleClickable

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun LogOutIcon(animScale: Float) {
    with(mainDep) {
        Box(
            Modifier
                .offset(width - 63.dp - infoOffset.value, 0.dp)
                .scale(animScale)
        ) {

            Icon(
                Icons.AutoMirrored.Sharp.Logout, null,
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable(
                        remember {
                            {
                                firebaseAuth.signOut()
                                if (firebaseAuth.currentUser?.uid == null) {
                                    sPreferences.saveIconUri(Uri.EMPTY)
                                    iconUri.value = Uri.EMPTY
                                }
                            }
                        }), tint = Color.White
            )
        }
    }
}