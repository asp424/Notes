package com.lm.notes.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lm.notes.data.local_data.NoteData.Base.Companion.NEW_TAG
import com.lm.notes.data.models.NavControllerScreens
import com.lm.notes.data.models.UiStates
import com.lm.notes.presentation.MainActivity
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.ui.cells.view.EditTextController
import com.lm.notes.ui.core.SpanType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

val <T> T.log get() = Log.d("My", toString())

fun Context.longToast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    composed {
        clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
    }

@Composable
fun animDp(target: Boolean, first: Dp, second: Dp, delay: Int = 300) = animateDpAsState(
    if (target) first else second, tween(delay)
).value

@Composable
fun animScaleDynamic(target: Boolean, first: Float, second: Float, duration: Int = 300) =
    animateFloatAsState(
        if (target) first else second, tween(duration)
    ).value

@Composable
fun shareDp(start: Dp, getIsExpandShare: Boolean, width: Dp, delay: Int = 300) = animDp(
    target = getIsExpandShare,
    first = width - start,
    second = width - 126.dp,
    delay = delay
)

fun formatTimestamp(timestamp: Long): String {
    val timeWas = timestamp.toString().asTime()
    val dateNow = Calendar.getInstance(Locale.getDefault())
    val monthLitWas = formatDate("MMM", timestamp)
    val monthNumWas = formatDate("MM", timestamp)
    val yearWas = formatDate("yyyy", timestamp)
    val yearWasSmall = formatDate("yy", timestamp)
    val dayOfMonthWas = formatDate("d", timestamp)
    val dayOfYearWas = formatDate("D", timestamp)
    val yearNow = dateNow.get(Calendar.YEAR)
    return when (dateNow.get(Calendar.DAY_OF_YEAR) - dayOfYearWas.toInt()) {
        1 -> "вчера $timeWas"
        2 -> "позавчера $timeWas"
        0 -> timeWas
        else -> {
            when (yearNow - yearWas.toInt()) {
                1 -> " $dayOfMonthWas.$monthNumWas.$yearWasSmall в $timeWas"
                0 -> " $dayOfMonthWas $monthLitWas в $timeWas"
                else -> " $yearWas год"
            }
        }
    }
}

private fun formatDate(value: String, date: Long) =
    SimpleDateFormat(value, Locale.getDefault()).format(date)

fun nowDate(date: Long): String = "$NEW_TAG${formatDate("d-MM-yyyy H:mm", date)}"

private fun String.asTime(): String {
    val time = Date(toLong())
    val timeFormat = SimpleDateFormat("H:mm", Locale.getDefault())
    return timeFormat.format(time)
}

fun backPressHandle(
    notesViewModel: NotesViewModel,
    mainActivity: MainActivity
) {
    with(notesViewModel) {
        with(editTextController) {
            with(uiStates) {
                if (getNavControllerScreens == NavControllerScreens.Main) {
                    if (getSettingsVisible || getIsDeleteMode) {
                        false.setSettingsVisible
                        cancelDeleteMode()
                    } else mainActivity.finish()
                } else {
                    if (getIsFormatMode) {
                        setEditMode(); onClickEditText(); editText.clearFocus()
                    } else {
                        NavControllerScreens.Main.setNavControllerScreen; setEditMode()
                        with(notesViewModel) {
                            if (isMustRemoveFromList()) deleteNote(noteModelFullScreen.value.id)
                        }
                    }
                }
                removeSelection()
            }
        }
    }
}

fun String.getHeader(isNew: Boolean) = if (isNew)
    substringAfter(NEW_TAG) else ifEmpty { "No name" }

fun EditTextController.getAction(uiStates: UiStates, spanType: SpanType) = with(uiStates) {
    with(spanType) {
        when (spanType) {
            is SpanType.Background -> ifNoSpans()
            is SpanType.Foreground -> ifNoSpans()
            else -> setSpan()
        }
    }
}

inline fun <T> List<T>.forEachInList(
    action: T.() -> Unit
) {
    for (element in this) action(element)
}



