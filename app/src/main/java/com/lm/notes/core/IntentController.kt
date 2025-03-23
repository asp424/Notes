package com.lm.notes.core

import android.content.Intent
import androidx.lifecycle.LifecycleCoroutineScope
import com.lm.notes.presentation.BaseActivity
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.ui.cells.view.app_widget.ToastCreator
import com.lm.notes.utils.log
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
                            getStringExtra(Intent.EXTRA_TEXT).log
                            result(IntentStates.SendPlain(getStringExtra(Intent.EXTRA_TEXT)?:""))
                            //toastCreator.invoke(R.string.text_plain)
}
                    Intent.ACTION_VIEW -> {
                        if ("text/plain" == type) {
                            result(IntentStates.ViewPlain(data))
                            //toastCreator.invoke(R.string.text_all)
                        }

                        if ("application/msword" == type) {
                            result(IntentStates.Word(getStringExtra(Intent.EXTRA_TEXT)?:""))
                           // toastCreator.invoke(R.string.application_msword)
                        }
                    }
                    BaseActivity.IS_AUTH_ACTION -> {
                        notesViewModel.synchronize(lifecycleScope)
                        with(notesViewModel.uiStates){
                            true.setIsAuth
                        }
                    }
                    else -> {
                        result(IntentStates.Null)
                        //toastCreator.invoke(R.string.empty_intent)
                    }
                }
            }
        }
    }
}