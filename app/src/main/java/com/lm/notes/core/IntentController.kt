package com.lm.notes.core

import android.content.Intent
import androidx.core.text.toHtml
import androidx.core.text.toSpanned
import androidx.lifecycle.LifecycleCoroutineScope
import com.lm.notes.data.local_data.FilesProvider
import com.lm.notes.data.models.IntentStates
import com.lm.notes.data.models.IntentType
import com.lm.notes.data.models.NavControllerScreens
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.ui.cells.view.app_widget.ToastCreator
import javax.inject.Inject


interface IntentController {

    fun checkForIntentAction(
        intent: Intent?,
        nVM: NotesViewModel,
        lifecycleScope: LifecycleCoroutineScope,
        result: (IntentStates) -> Unit
    )

    class Base @Inject constructor(
        private val toastCreator: ToastCreator,
        private val filesProvider: FilesProvider
    ) : IntentController {

        override fun checkForIntentAction(
            intent: Intent?,
            nVM: NotesViewModel,
            lifecycleScope: LifecycleCoroutineScope,
            result: (IntentStates) -> Unit
        ) {
            intent?.apply {
                with(nVM) {
                    with(uiStates) {
                        with(editTextController) {
                            when (type) {
                                IntentType.SendPlain.type -> {
                                    when (action) {
                                        Intent.ACTION_SEND -> {
                                            createEditText()
                                            with(editTextController) {
                                                getStringExtra(Intent.EXTRA_TEXT)?.toSpanned()
                                                    ?.apply {
                                                        addNewNote(lifecycleScope, this)
                                                        setNewText(toHtml())
                                                        editText.post { editText.lineCount.setLinesCounter }
                                                        false.setTranslateEnable
                                                        false.setIsSelected
                                                    }
                                            }
                                            NavControllerScreens.Note.setNavControllerScreen
                                        }

                                        Intent.ACTION_VIEW -> {
                                            createEditText()
                                            if (!getIsDeleteMode) {
                                                NavControllerScreens.Note.setNavControllerScreen
                                            }
                                            addNewNote(lifecycleScope) {
                                                filesProvider.readTextFileFromDevice(data) {
                                                    setNewText(toSpanned().toHtml())
                                                    updateNoteFromUi(toSpanned())
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}