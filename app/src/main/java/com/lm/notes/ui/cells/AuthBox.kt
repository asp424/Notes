package com.lm.notes.ui.cells

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.ui.cells.icons.AuthStatusIcon
import com.lm.notes.utils.animDp

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun MainDependencies.AuthBox(size: Dp) = with(notesViewModel.uiStates) {
    Box(
        Modifier
            .offset(animDp(authButtonMenuVisibility.value, 20.dp, 0.dp))
            .iconVisibility(getIsMainMode)
    ) {
        Button({}, Modifier.size(size), colors = ButtonDefaults.buttonColors(White)) {}
        AuthStatusIcon(size)
        if (progressVisibility.value) CircularProgressIndicator(
            Modifier.size(size), getSecondColor
        )
    }
}
