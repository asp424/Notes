package com.lm.notes.utils

import android.content.Context
import android.graphics.Color.WHITE
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.lm.notes.data.local_data.NoteData.Base.Companion.NEW_TAG
import com.lm.notes.data.models.NoteModel
import com.lm.notes.data.models.UiStates
import com.lm.notes.presentation.MainActivity
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.ui.cells.view.SpanType
import com.lm.notes.ui.cells.view.SpansProvider
import kotlinx.coroutines.CoroutineScope
import java.text.SimpleDateFormat
import java.util.*

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
fun animDp(target: Boolean, first: Dp, second: Dp, delay: Int) = animateDpAsState(
    if (target) first else second, tween(delay)
).value

@Composable
fun animFloat(target: Boolean, first: Float, second: Float) = animateFloatAsState(
    if (target) first else second, tween(100)
).value

@Composable
fun animScale(target: Boolean) = animateFloatAsState(
    if (target) 1f else 0f, tween(100)
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
        1 -> "yesterday $timeWas"
        2 -> "the day before yesterday $timeWas"
        0 -> timeWas
        else -> {
            when (yearNow - yearWas.toInt()) {
                1 -> " $dayOfMonthWas.$monthNumWas.$yearWasSmall at $timeWas"
                0 -> " $dayOfMonthWas $monthLitWas в $timeWas"
                else -> " $yearWas year"
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

fun MainActivity.backPressHandle(
    navController: NavController,
    notesViewModel: NotesViewModel,
    coroutine: CoroutineScope,
    noteModel: NoteModel,
    uiStates: UiStates
) {
    if (navController.currentDestination?.route == "mainList") finish()
    else {
        if (uiStates.getLongClickState) {
            uiStates.hideFormatPanel()
            editTextProvider.setEditMode()
        }
        else {
            navController.navigate("mainList")
            with(notesViewModel) {
                if (isMustRemoveFromList())
                    notesViewModel.deleteNote(coroutine, noteModel.id)
            }
        }
    }
}

fun ImageVector.getTint(uiStates: UiStates) = with(uiStates) {
    when (this@getTint) {
        Icons.Rounded.FormatColorFill -> getColorButtonBackground
        Icons.Rounded.FormatColorText -> getColorButtonForeground
        Icons.Rounded.FormatUnderlined -> getColorButtonUnderlined
        Icons.Rounded.FormatBold -> getColorButtonBold
        Icons.Rounded.FormatItalic -> getColorButtonItalic
        Icons.Rounded.FormatStrikethrough -> getColorButtonStrikeThrough
        else -> Color.Black
    }
}

fun ImageVector.getSpanType(uiStates: UiStates) = with(uiStates) {
    when (this@getSpanType) {
        Icons.Rounded.FormatColorFill -> SpanType.Background(getColorButtonBackground.toArgb())
        Icons.Rounded.FormatColorText -> SpanType.Foreground(getColorButtonForeground.toArgb())
        Icons.Rounded.FormatUnderlined -> SpanType.Underlined
        Icons.Rounded.FormatBold -> SpanType.Bold
        Icons.Rounded.FormatItalic -> SpanType.Italic
        Icons.Rounded.FormatStrikethrough -> SpanType.StrikeThrough
        else -> SpanType.ColoredUnderlined(WHITE)
    }
}

fun SpansProvider.getAction(uiStates: UiStates, spanType: SpanType) = with(uiStates) {
    with(spanType) {
        when (spanType) {
            is SpanType.Background -> ifNoSpans()
            is SpanType.Foreground -> ifNoSpans()
            else -> setSpan()
        }
    }
}


