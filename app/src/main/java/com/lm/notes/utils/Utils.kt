package com.lm.notes.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

val <T> T.log get() = Log.d("Asshole", toString())

var isAuth: MutableState<Boolean> = mutableStateOf(false)

val getIsAuth get() = isAuth.value

val Boolean.setIsAuth get() = run { isAuth.value = this }

val iconUri = mutableStateOf(Uri.EMPTY)

val getIconUri: Uri get() = iconUri.value

val Uri.setIconUri get() = run { iconUri.value = this }

fun Context.longToast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()


@Composable
fun animDp(target: Boolean, first: Dp, second: Dp = 0.dp , delay: Int = 800) = animateDpAsState(
    if (target) first else second, tween(delay)
).value

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
                        onClickEditText()
                        editText.clearFocus()
                        setEditMode()
                    } else {
                        NavControllerScreens.Main.setNavControllerScreen
                        with(notesViewModel) {
                            if (isMustRemoveFromList()) deleteNote(noteModelFullScreen.value.id)
                        }
                        setEditMode()
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




