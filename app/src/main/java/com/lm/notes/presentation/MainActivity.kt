package com.lm.notes.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalView
import androidx.core.app.ShareCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.R
import com.lm.notes.core.Shortcuts
import com.lm.notes.core.appComponentBuilder
import com.lm.notes.data.local_data.FilesProvider
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.di.compose.CustomTextToolbar
import com.lm.notes.di.compose.mainScreenDependencies
import com.lm.notes.ui.MainScreen
import com.lm.notes.ui.theme.NotesTheme
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

    @Inject
    lateinit var filesProvider: FilesProvider

    @Inject
    lateinit var shortcuts: Shortcuts

    private val notesViewModel by viewModels<NotesViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponentBuilder.intentBuilder(ShareCompat.IntentBuilder(this))
            .create().inject(this)

        if (intent.action.toString() == IS_AUTH_ACTION) {
            notesViewModel.synchronize(lifecycleScope)
        }

        setContent {
            NotesTheme() {
                mainScreenDependencies(
                    sPreferences,
                    viewModelFactory,
                    firebaseAuth,
                    filesProvider
                ) {
                    CompositionLocalProvider(
                        LocalTextToolbar provides CustomTextToolbar(LocalView.current)
                    ) {
                        MainScreen()
                    }
                }
            }
        }

        shortcuts.disableShortcut("ass")
        shortcuts.pushShortcut(
            "ass1", "ass", "asshole1",
            Intent(this, MainActivity::class.java).apply { action = "ass" },
            R.drawable.notebook_list
        )
    }

    override fun onPause() {
        super.onPause()
        CoroutineScope(coroutineDispatcher).launch { notesViewModel.updateChangedNotes() }
    }
}

