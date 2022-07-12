package com.lm.notes.presentation

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.core.appComponent
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.di.compose.MainScreenDependencies
import com.lm.notes.ui.MainScreen
import com.lm.notes.ui.theme.NotesTheme
import com.lm.notes.utils.log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var sPreferences: SPreferences

    @Inject
    lateinit var coroutineDispatcher: CoroutineDispatcher

    private val notesViewModel by viewModels<NotesViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DisplayMetrics().apply {
            appComponent.inject(this@MainActivity)
            if (intent.action.toString() == IS_AUTH_ACTION)
                notesViewModel.synchronize(lifecycleScope)

            setContent {
                NotesTheme() {
                    MainScreenDependencies(sPreferences, viewModelFactory, firebaseAuth) {
                        MainScreen()
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        CoroutineScope(coroutineDispatcher).launch { notesViewModel.updateChangedNotes() }
    }
}
