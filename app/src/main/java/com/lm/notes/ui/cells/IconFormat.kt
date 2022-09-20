package com.lm.notes.ui.cells

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lm.notes.data.models.UiStates
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.view.SpansProvider
import com.lm.notes.utils.getAction
import com.lm.notes.utils.getSpanType
import com.lm.notes.utils.getTint
import com.lm.notes.utils.noRippleClickable

@Composable
fun IconFormat(source: ImageVector) {
    with(mainDep) {
        val click by derivedStateOf { { source.buttonFormatAction(spansProvider, uiStates) } }

        Box(modifier = Modifier.padding(start = 10.dp)) {
            Icon(
                source, null,
                modifier = Modifier.noRippleClickable(click),
                tint = source.getTint(uiStates)
            )
        }
    }
}

private fun ImageVector.buttonFormatAction(
    spansProvider: SpansProvider, uiStates: UiStates
) = with(
    spansProvider
) {
    with(getSpanType(uiStates)) {
        if (isHaveSpans()) removeSpan() else getAction(uiStates, this)
    }
}
