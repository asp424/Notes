package com.lm.notes.core

import android.content.Intent
import androidx.lifecycle.LifecycleCoroutineScope
import com.lm.notes.data.models.IntentStates
import com.lm.notes.presentation.BaseActivity
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.ui.cells.view.app_widget.ToastCreator
import com.lm.notes.utils.setIsAuth
import javax.inject.Inject


interface IntentController {

    fun checkForIntentAction(
        intent: Intent?,
        notesViewModel: NotesViewModel,
        lifecycleScope: LifecycleCoroutineScope,
        result: (IntentStates) -> Unit
    )

    class Base @Inject constructor(
        private val toastCreator: ToastCreator
    ) : IntentController {

        override fun checkForIntentAction(
            intent: Intent?,
            notesViewModel: NotesViewModel,
            lifecycleScope: LifecycleCoroutineScope,
            result: (IntentStates) -> Unit
        ) {
            intent?.apply {
                when (type) {
                    "text/plain" -> {
                        result(
                            IntentStates.SendPlain(
                                text = getStringExtra(Intent.EXTRA_TEXT) ?: ""
                            )
                        )
                    }

                    Intent.ACTION_VIEW -> {
                        if ("text/plain" == type) {
                            result(IntentStates.ViewPlain(uri = data))
                        }

                        if ("application/msword" == type) {
                            result(
                                IntentStates.Word(
                                    inBox = getStringExtra(Intent.EXTRA_TEXT) ?: ""
                                )
                            )
                        }
                    }

                    BaseActivity.IS_AUTH_ACTION -> {
                        notesViewModel.synchronize(lifecycleScope)
                        with(notesViewModel.uiStates) {
                            true.setIsAuth
                        }
                    }
                }
            }
        }
    }
}