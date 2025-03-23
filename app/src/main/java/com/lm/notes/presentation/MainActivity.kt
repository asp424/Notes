package com.lm.notes.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.core.text.toHtml
import androidx.core.text.toSpanned
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.core.IntentController
import com.lm.notes.core.appComponent
import com.lm.notes.data.local_data.FilesProvider
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.data.models.IntentStates
import com.lm.notes.data.models.NavControllerScreens
import com.lm.notes.di.compose.MainDep.mainDep
import com.lm.notes.di.compose.MainScreenDependencies
import com.lm.notes.ui.cells.view.app_widget.NoteAppWidgetController
import com.lm.notes.ui.screens.MainScreen
import com.lm.notes.ui.theme.NotesTheme
import com.lm.notes.utils.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: dagger.Lazy<ViewModelFactory>

    @Inject
    lateinit var sPreferences: SPreferences

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var filesProvider: FilesProvider

    @Inject
    lateinit var noteAppWidgetController: NoteAppWidgetController

    @Inject
    lateinit var intentController: IntentController

    private val notesViewModel by viewModels<NotesViewModel> { viewModelFactory.get() }

    val chooseFolderPath =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { filesProvider.createAndSaveFile(it) }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContent {
            NotesTheme(viewModelFactory = viewModelFactory.get()) {
                MainScreenDependencies(
                    sPreferences,
                    viewModelFactory.get(),
                    firebaseAuth,
                    filesProvider,
                    noteAppWidgetController
                ) {
                    with(mainDep) { MainScreen() }
                    if (intent.type != "null")
                        LaunchedEffect(intent){
                            getDataFromIntent(intent)
                        }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        notesViewModel.clipboardProvider.addListener()
        with(notesViewModel) { uiStates.setSelection(lifecycleScope, this) }
    }

    override fun onPause() {
        super.onPause()
        notesViewModel.clipboardProvider.removeListener()
        CoroutineScope(IO).launch { notesViewModel.updateChangedNotes() }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.type != "null") getDataFromIntent(intent)
    }

    private fun getDataFromIntent(intent: Intent?) {
        "d: ${intent?.type}".log
        "d: ${intent?.action}".log
        intentController.checkForIntentAction(intent, notesViewModel, lifecycleScope)
        { i ->
            notesViewModel.editTextController.createEditText()
            with(notesViewModel.uiStates)
            { NavControllerScreens.Note.setNavControllerScreen }

            notesViewModel.addNewNote(lifecycleScope) {
                when (i) {
                    is IntentStates.SendPlain ->
                        notesViewModel.editTextController.setNewText(
                            i.text.toSpanned().toHtml()
                        )

                    is IntentStates.ViewPlain ->
                        filesProvider.readTextFileFromDeviceAndSetToEditText(
                            i.uri
                        )

                    is IntentStates.Word ->
                        notesViewModel.editTextController.setNewText(
                            i.inBox.toSpanned().toHtml()
                        )

                    is IntentStates.Content -> TODO()
                    IntentStates.Null -> TODO()
                }
                notesViewModel.checkForEmptyText()
            }
        }
    }
}


