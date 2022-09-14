package com.lm.notes.ui.bars

import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.unit.dp
import com.lm.notes.data.local_data.ShareType
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.ui.cells.ShareCanvasButton
import com.lm.notes.ui.theme.bar
import com.lm.notes.utils.animDp
import com.lm.notes.utils.noRippleClickable

@Composable
fun FullScreenBar(animScale: Float) {
    with(mainDep) {
        notesViewModel.noteModelFullScreen.value.apply {
            val isExpand = remember { mutableStateOf(false) }
            val asHtmlDp =
                animDp(
                    target = isExpand.value,
                    first = width - 140.dp,
                    second = width - 106.dp,
                    200
                )
            val asTxtDp =
                animDp(
                    target = isExpand.value,
                    first = width - 175.dp,
                    second = width - 106.dp,
                    400
                )
            val txtDp = animDp(
                target = isExpand.value,
                first = width - 210.dp,
                second = width - 106.dp,
                600
            )

            val paint = remember {
                Paint().asFrameworkPaint().apply {
                    isAntiAlias = true
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                }
            }
            ShareCanvasButton(asHtmlDp, click = {
                filesProvider.shareAsFile(
                    "${headerState.value.text}\n$text", ShareType.AsHtml,
                    timestampChangeState.value
                )
            }, isExpand, animScale, paint, ".html",-40f, 35f)

            ShareCanvasButton(asTxtDp, click = {
                filesProvider.shareAsFile(
                    "${headerState.value.text}\n\n${editTextProvider.fromHtml(text).toString()}",
                    ShareType.AsTxt,
                    timestampChangeState.value
                )
            }, isExpand, animScale, paint, ".txt", -35f, 45f)

            ShareCanvasButton(txtDp, click = {
                filesProvider.shareAsText(
                    editTextProvider
                        .fromHtml(text)
                        .toString()
                )
            }, isExpand, animScale, paint, "txt", -26f, 45f)
            Canvas(
                Modifier
                    .offset(width - 106.dp, 0.dp)
                    .scale(animScale)
            ) { drawCircle(bar, 15.dp.toPx(), Offset.Zero) }

            Modifier.offset(width - 120.dp, 0.dp).also { offset ->
                Box(offset.scale(animScale)) {
                    Icon(
                        Icons.Rounded.Share, null, modifier = Modifier
                            .noRippleClickable { isExpand.value = !isExpand.value }, tint = White
                    )
                }
            }
        }
    }
}
