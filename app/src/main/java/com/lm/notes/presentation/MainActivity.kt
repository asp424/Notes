package com.lm.notes.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.appComponent
import com.lm.notes.di.compose.ComposeDependencies
import com.lm.notes.ui.Screens
import com.lm.notes.utils.log
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var screens: Screens

    @Inject
    lateinit var composeDependencies: ComposeDependencies

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var viewModels: ViewModels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContent {
            composeDependencies.MainScreenDependencies {
                screens.MainScreen()
            }
        }
        intent.action.toString().log
        if (intent.action.toString() == IS_AUTH_ACTION)
            viewModels.viewModelProvider(this)[NotesViewModel::class.java]
               // .apply { lifecycleScope.notesList() }
    }
}
