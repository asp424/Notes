package com.lm.notes.presentation

import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lm.notes.data.SPreferences
import com.lm.notes.data.remote_data.registration.FBRegState
import com.lm.notes.data.remote_data.registration.OTGRegState
import com.lm.notes.data.remote_data.registration.OneTapGoogleAuth
import com.lm.notes.utils.toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val oneTapGoogleAuth: OneTapGoogleAuth,
    private val sPreferences: SPreferences
) : ViewModel(), DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        (owner as LoginActivity).also { loginActivity ->
            loginActivity.registerForActivityResult(
                ActivityResultContracts.StartIntentSenderForResult()
            ) { handleResult(loginActivity, it) }.apply {
                startOTGAuth(loginActivity, this)
            }
        }
    }

    private fun handleResult(loginActivity: LoginActivity, result: ActivityResult) =
        with(loginActivity) {
            viewModelScope.launch {
                launch {
                    delay(20000L); toast("Authorize error, try later")
                    startMainActivity
                }

                oneTapGoogleAuth.handleResultAndFBReg(result).collect {
                    when (it) {
                        is FBRegState.OnSuccess -> {
                            sPreferences.saveIconUri(it.iconUri); startMainActivity
                        }
                        is FBRegState.OnError -> {
                            toast(it.message); finish()
                        }
                        is FBRegState.Loading -> Unit
                    }
                }
            }
        }

    private fun startOTGAuth(
        loginActivity: LoginActivity, regLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) = viewModelScope.launch {
        oneTapGoogleAuth.startAuth().collect {
            when (it) {
                is OTGRegState.OnSuccess ->
                    regLauncher.launch(IntentSenderRequest.Builder(it.intentSender).build())
                is OTGRegState.OnError -> loginActivity.toast(it.message)
            }
        }
    }
}