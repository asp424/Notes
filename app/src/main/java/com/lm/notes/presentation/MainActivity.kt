package com.lm.notes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.core.app.ShareCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.core.appComponentBuilder
import com.lm.notes.data.local_data.FilesProvider
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.databinding.EditTextBinding
import com.lm.notes.di.compose.mainScreenDependencies
import com.lm.notes.ui.screens.MainScreen
import com.lm.notes.ui.theme.NotesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: dagger.Lazy<ViewModelFactory>

    private val notesViewModel by viewModels<NotesViewModel> { viewModelFactory.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponentBuilder.intentBuilder { ShareCompat.IntentBuilder(this) }
            .editText(EditTextBinding.inflate(LayoutInflater.from(this)).root)
            .create().inject(this)
        if (intent.action.toString() == IS_AUTH_ACTION) {
            notesViewModel.synchronize(lifecycleScope)
        }
    }

    @Inject
    fun start(
        sPreferences: SPreferences,
        viewModelFactory: ViewModelFactory,
        firebaseAuth: FirebaseAuth,
        filesProvider: FilesProvider
    ) {
        setContent {
            NotesTheme(viewModelFactory = viewModelFactory) {
                mainScreenDependencies(sPreferences, viewModelFactory, firebaseAuth, filesProvider)
                {
                    val notesViewModel by viewModels<NotesViewModel> { viewModelFactory }
                    LaunchedEffect(true){
                        notesViewModel.uiStates.apply {
                            Color(sPreferences.readMainColor()).setMainColor
                        }
                    }
                    MainScreen() }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenResumed {
            delay(300)
            with(notesViewModel.uiStates) {
                notesViewModel.spansProvider.setSelection()
                notesViewModel.clipboardProvider.clipBoardIsNotEmpty?.setClipboardIsEmpty
            }
        }
    }

    override fun onPause() {
        super.onPause()
        CoroutineScope(IO).launch { notesViewModel.updateChangedNotes() }
    }
}

