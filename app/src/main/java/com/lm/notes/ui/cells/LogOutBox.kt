package com.lm.notes.ui.cells

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.utils.noRippleClickable

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun MainDependencies.LogOutBox(size: Dp) {
    Box(
        Modifier
            .offset(-authButtonMenuOffsetY, 0.dp)
            .iconVisibility(notesViewModel.uiStates.getIsMainMode)
    ) {
        Button(
            onClick = {}, modifier = Modifier
                .size(size), colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
        }
        Icon(
            Icons.AutoMirrored.Sharp.Logout, null,
            modifier = Modifier
                .size(size - 2.dp)
                .offset(x = 5.dp)
                .noRippleClickable(
                    remember {
                        {
                            firebaseAuth.signOut()
                            if (firebaseAuth.currentUser?.uid == null) {
                                sPreferences.saveIconUri(Uri.EMPTY)
                                iconUri.value = Uri.EMPTY
                                with(notesViewModel.uiStates) {
                                    false.setIsAuth
                                }
                            }
                        }
                    }), tint = Color.White
        )
    }
}