package com.lm.notes.ui.bars

import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lm.notes.data.local_data.NoteData.Base.Companion.NEW_TAG
import com.lm.notes.data.local_data.ShareType
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.MainActivity
import com.lm.notes.ui.cells.ShareCanvasButton
import com.lm.notes.ui.theme.bar
import com.lm.notes.utils.animDp
import com.lm.notes.utils.log
import com.lm.notes.utils.noRippleClickable

@Composable
fun FullScreenBar(animScale: Float) {
    with(mainDep) {
        notesViewModel.noteModelFullScreen.value.apply {
            LocalDensity.current.apply {
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
                        "${
                            with(headerState.value.text) {
                                header(notesViewModel.isNewHeader(this))
                            }
                        }\n\n$text", ShareType.AsHtml,
                        timestampChangeState.value
                    )
                }, isExpand, animScale, paint, ".html", -40f, 12.dp.toPx())

                ShareCanvasButton(asTxtDp, click = {
                    filesProvider.shareAsFile(
                        "${
                            with(headerState.value.text) {
                                header(notesViewModel.isNewHeader(this))
                            }
                        }\n\n${spansProvider.fromHtml(text).toString()}",
                        ShareType.AsTxt,
                        timestampChangeState.value
                    )
                }, isExpand, animScale, paint, ".txt", -35f, 14.dp.toPx())

                (LocalContext.current as MainActivity).apply {
                    ShareCanvasButton(txtDp, click = {
                        filesProvider.shareAsText(text)
                    }, isExpand, animScale, paint, "txt", -26f, 14.dp.toPx())
                }
                Canvas(
                    Modifier
                        .offset(width - 106.dp, 0.dp)
                        .scale(animScale)
                ) { drawCircle(bar, 18.dp.toPx(), Offset.Zero) }

                Box(
                    Modifier
                        .offset(width - 120.dp, 0.dp)
                        .scale(animScale)
                ) {
                    Icon(
                        Icons.Rounded.Share,
                        null,
                        modifier = Modifier
                            .noRippleClickable { isExpand.value = !isExpand.value },
                        tint = White
                    )
                }
            }
        }
    }
}

fun String.header(isNew: Boolean) = if (isNew) substringAfter(NEW_TAG) else ifEmpty { "No name" }
