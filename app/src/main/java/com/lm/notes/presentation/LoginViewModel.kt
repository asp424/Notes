package com.lm.notes.presentation

import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.data.remote_data.registration.FBRegState
import com.lm.notes.data.remote_data.registration.OTGRegState
import com.lm.notes.data.remote_data.registration.OneTapGoogleAuth
import com.lm.notes.utils.toast
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val oneTapGoogleAuth: OneTapGoogleAuth,
    private val sPreferences: SPreferences
) : ViewModel(), DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        (owner as LoginActivity).apply {
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult())
            { handleResult(it) }.apply { startOTGAuth(this) }
        }
    }

    private fun LoginActivity.handleResult(result: ActivityResult) =
        viewModelScope.launch(IO) {
            launch(Main) {
                delay(20000L); toast("Authorize error, try later")
                startMainActivity
            }
            oneTapGoogleAuth.handleResultAndFBReg(result).collect {
                when (it) {
                    is FBRegState.OnSuccess -> sPreferences.saveIconUri(it.iconUri)
                    is FBRegState.OnError -> toast(it.message)
                    is FBRegState.OnClose -> { delay(1000) }
                }
                startMainActivity
            }
        }

    private fun LoginActivity.startOTGAuth(
        regLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) = oneTapGoogleAuth.startAuth() {
        when (it) {
            is OTGRegState.OnSuccess ->
                regLauncher.launch(IntentSenderRequest.Builder(it.intentSender).build())
            is OTGRegState.OnError -> {
                toast(it.message); startMainActivity
            }
        }
    }
}