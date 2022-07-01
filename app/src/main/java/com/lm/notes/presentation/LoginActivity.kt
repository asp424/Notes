package com.lm.notes.presentation

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val loginViewModel: LoginViewModel by viewModels { viewModelFactory }

    val regLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult())
        { loginViewModel.handleResult(this, it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        regComponent.inject(this)
        lifecycle.addObserver(loginViewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(loginViewModel)
    }
}