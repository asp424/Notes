package com.lm.notes.ui.bars

import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Widgets
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.lm.notes.utils.animDp
import com.lm.notes.utils.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FullScreenBar(animScaleShare: Float, animScaleNotShare: Float) {
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                with(editTextController) {
                    noteModelFullScreen.collectAsState().value.apply {
                        LocalDensity.current.apply {
                            val asHtmlDp =
                                animDp(
                                    target = getIsExpandShare,
                                    first = width - 160.dp,
                                    second = width - 126.dp,
                                    200
                                )
                            val asTxtDp =
                                animDp(
                                    target = getIsExpandShare,
                                    first = width - 195.dp,
                                    second = width - 126.dp,
                                    400
                                )
                            val txtDp = animDp(
                                target = getIsExpandShare,
                                first = width - 230.dp,
                                second = width - 126.dp,
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
                                            header(isNewHeader(this))
                                        }
                                    }\n\n$text", ShareType.AsHtml,
                                    timestampChangeState.value
                                )
                            }, animScaleShare, paint, ".html", -40f, 12.dp.toPx())

                            ShareCanvasButton(asTxtDp, click = {
                                filesProvider.shareAsFile(
                                    "${
                                        with(headerState.value.text) {
                                            header(isNewHeader(this))
                                        }
                                    }\n\n${fromHtml(text)}",
                                    ShareType.AsTxt,
                                    timestampChangeState.value
                                )
                            }, animScaleShare, paint, ".txt", -35f, 14.dp.toPx())

                            (LocalContext.current as MainActivity).apply {
                                ShareCanvasButton(txtDp, click = {
                                    filesProvider.shareAsText(text)
                                }, animScaleShare, paint, "txt", -26f, 14.dp.toPx())
                            }
                            Canvas(
                                Modifier
                                    .offset(width - 126.dp, 0.dp)
                                    .scale(animScaleShare)
                            ) { drawCircle(getMainColor, 18.dp.toPx(), Offset.Zero) }

                            Box(
                                Modifier
                                    .offset(width - 135.dp, 0.dp)
                                    .scale(animScaleShare)
                            ) {
                                Icon(
                                    Icons.Rounded.Share,
                                    null,
                                    modifier = Modifier.noRippleClickable { expandShare(coroutine) },
                                    tint = White
                                )
                            }
                            Box(
                                Modifier
                                    .offset(width - 195.dp, 0.dp)
                                    .scale(animScaleNotShare)
                            ) {
                                Icon(
                                    Icons.Rounded.Widgets,
                                    null,
                                    modifier = Modifier
                                        .noRippleClickable {
                                            noteAppWidgetController
                                                .pinNoteWidget(
                                                    Pair(fromHtml(editText.text.toString())
                                                        .toString(),
                                                        headerState.value.text
                                                    )
                                                )
                                        },
                                    tint = White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun String.header(isNew: Boolean) = if (isNew) substringAfter(NEW_TAG) else ifEmpty { "No name" }
