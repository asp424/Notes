package com.lm.notes.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import dagger.Lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>

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

    private val nVM by viewModels<NotesViewModel> { viewModelFactory.get() }

    val chooseFolderPath =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { filesProvider.createAndSaveFile(it) }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContent {
            NotesTheme(nVM) {
                MainScreenDependencies(
                    sPreferences, firebaseAuth, filesProvider, noteAppWidgetController, nVM
                ) {
                    mainDep.MainScreen(this@MainActivity, lifecycleScope)
                    getDataFromIntent(intent)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        nVM.clipboardProvider.addListener()
        with(nVM) { uiStates.setSelection(lifecycleScope, this) }
    }

    override fun onPause() {
        super.onPause()
        nVM.clipboardProvider.removeListener()
        CoroutineScope(IO).launch { nVM.updateChangedNotes() }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.type != "null") getDataFromIntent(intent)
    }

    private fun getDataFromIntent(intent: Intent?) {
        intentController.checkForIntentAction(intent, nVM, lifecycleScope)
        { i ->
            nVM.editTextController.createEditText()
            with(nVM.uiStates)
            { NavControllerScreens.Note.setNavControllerScreen }

            nVM.addNewNote(lifecycleScope) {
                when (i) {
                    is IntentStates.SendPlain ->
                        nVM.editTextController.setNewText(
                            i.text.toSpanned().toHtml()
                        )

                    is IntentStates.ViewPlain ->
                        filesProvider.readTextFileFromDevice(
                            i.uri
                        )

                    is IntentStates.Word ->
                        nVM.editTextController.setNewText(
                            i.inBox.toSpanned().toHtml()
                        )

                    is IntentStates.Content -> TODO()
                    IntentStates.Null -> TODO()
                }
                nVM.checkForEmptyText()
            }
        }
    }
}





