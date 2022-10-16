package com.lm.notes.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.core.IntentController
import com.lm.notes.core.appComponent
import com.lm.notes.data.local_data.FilesProvider
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.di.compose.MainScreenDependencies
import com.lm.notes.ui.cells.view.app_widget.NoteAppWidgetController
import com.lm.notes.ui.screens.MainScreen
import com.lm.notes.ui.theme.NotesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: dagger.Lazy<ViewModelFactory>

    private val notesViewModel by viewModels<NotesViewModel> { viewModelFactory.get() }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        with(notesViewModel) { uiStates.setSelection(lifecycleScope, this) }
    }

    override fun onPause() {
        super.onPause()
        CoroutineScope(IO).launch { notesViewModel.updateChangedNotes() }
    }

    @Inject
    fun showUI(
        sPreferences: SPreferences,
        viewModelFactory: ViewModelFactory,
        firebaseAuth: FirebaseAuth,
        filesProvider: FilesProvider,
        noteAppWidgetController: NoteAppWidgetController
    ) {
        setContent {
            NotesTheme(viewModelFactory = viewModelFactory) {
                MainScreenDependencies(
                    sPreferences, viewModelFactory, firebaseAuth, filesProvider,
                    noteAppWidgetController
                )
                { MainScreen() }
            }
        }
    }

    @Inject
    fun checkIntentAction(intentController: IntentController) = intentController
        .checkForIntentAction(intent, notesViewModel, lifecycleScope)
}

