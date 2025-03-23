package com.lm.notes.core

import android.content.Intent
import androidx.core.text.toHtml
import androidx.core.text.toSpanned
import androidx.lifecycle.LifecycleCoroutineScope
import com.lm.notes.R
import com.lm.notes.data.local_data.FilesProvider
import com.lm.notes.data.models.IntentStates
import com.lm.notes.data.models.NavControllerScreens
import com.lm.notes.presentation.BaseActivity
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.ui.cells.view.app_widget.ToastCreator
import javax.inject.Inject


interface IntentController {

    fun checkForIntentAction(
        intent: Intent?,
        notesViewModel: NotesViewModel,
        lifecycleScope: LifecycleCoroutineScope,
        result: (IntentStates) -> Unit
    )

    class Base @Inject constructor(
        private val toastCreator: ToastCreator,
        private val filesProvider: FilesProvider
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
                        if ((getStringExtra(Intent.EXTRA_TEXT))?.isNotEmpty() == true){
                            notesViewModel.editTextController.createEditText()
                            with(notesViewModel.uiStates)
                            { NavControllerScreens.Note.setNavControllerScreen }
                            notesViewModel.addNewNote(lifecycleScope) { id ->
                                notesViewModel.editTextController.setNewText(
                                    (getStringExtra(Intent.EXTRA_TEXT) ?: "").toSpanned().toHtml()
                                )
                                notesViewModel.setFullscreenNoteModel(id)

                                if (!notesViewModel.uiStates.getTranslateEnable)
                                    notesViewModel.updateNoteFromUi(
                                        (getStringExtra(Intent.EXTRA_TEXT) ?: "").toSpanned()
                                    )
                            }

                        } else toastCreator(R.string.empty_file)
                    }

                    "file" -> result(
                        IntentStates.ViewPlain(
                         data
                        )
                    )

                    "application/msword" -> result(
                        IntentStates.Word(getStringExtra(Intent.EXTRA_TEXT) ?: "")
                    )

                    "content" -> result(
                        IntentStates.Null
                    )

                    BaseActivity.IS_AUTH_ACTION -> notesViewModel.synchronize(lifecycleScope)
                }
            }
        }
    }
}
