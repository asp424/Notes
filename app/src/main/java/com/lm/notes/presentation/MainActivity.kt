package com.lm.notes.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.appComponent
import com.lm.notes.data.SPreferences
import com.lm.notes.di.compose.ComposeDependencies
import com.lm.notes.ui.Screens
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var screens: Screens

    @Inject
    lateinit var composeDependencies: ComposeDependencies

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContent {
            composeDependencies.MainScreenDependencies {
                screens.MainScreen()
            }
        }
    }
}
