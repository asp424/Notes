package com.lm.notes.ui.cells.icons

import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.Sort
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lm.notes.di.compose.MainDependencies
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.utils.getIsAuth
import com.lm.notes.utils.noRippleClickable

@Composable
fun MainDependencies.MainBarIcon(
    icon: ImageVector,
    offsetY: Dp = 0.dp
) = with(notesViewModel) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    Icon(
        icon, null,
        Modifier
            .iconVisibility(
                with(uiStates) {
                    if (icon == Icons.Rounded.Public)
                        getIsAuth && getIsMainMode else getIsMainMode
                }
            )
            .offset(x = offsetY)
            .noRippleClickable(getAction(icon, lifecycleScope)),
        uiStates.getSecondColor
    )
}

@Composable
fun NotesViewModel.getAction(
    icon: ImageVector,
    lifecycleScope: LifecycleCoroutineScope
): () -> Unit = when (icon) {
    Icons.Rounded.Settings -> uiStates.settingsIconClick
    Icons.AutoMirrored.Sharp.Sort -> remember { { sortByCreate() } }
    Icons.Rounded.SwapVert -> remember { { uiStates.setReversLayout() } }
    Icons.Rounded.Public -> remember {
        { downloadNotesFromFirebase(lifecycleScope) }
    }

    else -> {
        {}
    }
}