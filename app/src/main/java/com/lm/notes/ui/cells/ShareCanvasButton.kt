package com.lm.notes.ui.cells

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lm.notes.data.local_data.ShareType
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.presentation.MainActivity
import com.lm.notes.utils.getHeader
import com.lm.notes.utils.modifiers.noRippleClickable
import com.lm.notes.utils.shareDp

@SuppressLint("ContextCastToActivity")
@Composable
fun ShareCanvasButton(
    x: Dp,
    shareType: ShareType = ShareType.TextPlain,
    start: Dp = 108.dp, y: Dp = 0.dp
) {
    val configuration = LocalConfiguration.current
    val coroutine = rememberCoroutineScope()
    with(mainDep) {
        with(notesViewModel) {
            with(uiStates) {
                noteModelFullScreen.collectAsState().value.apply {
                    with(filesProvider) {
                        val paint = remember {
                            Paint().asFrameworkPaint().apply {
                                isAntiAlias = true
                                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                                color =
                                    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                                        getSecondColor.toArgb() else getMainColor.toArgb()
                            }
                        }
                        val activity = LocalContext.current as MainActivity
                        val conf = LocalConfiguration.current
                        Canvas(
                            Modifier
                                .offset(shareDp(x, getIsExpandShare, start), y)
                                .noRippleClickable(
                                    remember(this@apply) {
                                        {
                                            val header = with(header) {
                                                getHeader(isNewHeader(this))
                                            }
                                            if (shareType == ShareType.TextPlain)
                                                shareAsText(text, activity)
                                            else shareAsFile(shareType, header, activity)
                                            expandShare(coroutine)
                                        }
                                    })
                                .iconVisibility(getNoteMode && getTextIsEmpty)
                        ) {
                            drawCircle(
                                if (conf.orientation == Configuration.ORIENTATION_LANDSCAPE)
                                    getMainColor else getSecondColor, 15.dp.toPx(), Offset.Zero
                            )
                            paint.apply {
                                textSize =
                                    (if (shareType == ShareType.AsHtml) 11.dp else 14.dp).toPx()
                            }

                            drawIntoCanvas {
                                it.nativeCanvas.drawText(
                                    shareType.text, -12.dp.toPx(), 5.dp.toPx(), paint
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



