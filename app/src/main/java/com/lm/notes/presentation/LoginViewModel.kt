package com.lm.notes.presentation

import android.content.IntentSender
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.*
import com.google.android.gms.auth.api.identity.SignInClient
import com.lm.notes.data.SPreferences
import com.lm.notes.data.remote_data.registration.FBAuth
import com.lm.notes.data.remote_data.registration.FBRegState
import com.lm.notes.data.remote_data.registration.OTGRegState
import com.lm.notes.data.remote_data.registration.OneTapGoogleAuth
import com.lm.notes.di.dagger.RegComponent
import com.lm.notes.utils.log
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
        startOTGAuth(owner)
    }

    fun handleResult(owner: LifecycleOwner, result: ActivityResult)
    = with(owner as LoginActivity){
        viewModelScope.launch {
            launch {
                delay(20000L); toast("Authorize error");
                startMainActivity
            }

            oneTapGoogleAuth.handleResultAndFBReg(result).collect {
                when (it) {
                    is FBRegState.OnSuccess -> {
                        sPreferences.saveIconUri(it.iconUri); startMainActivity
                    }
                    is FBRegState.OnError -> { toast(it.message); finish() }
                    is FBRegState.Loading -> Unit
                }
            }
        }
    }

    private fun startOTGAuth(owner: LifecycleOwner) =
        with(owner as LoginActivity){
            viewModelScope.launch {
                oneTapGoogleAuth.startAuth().collect {
                    when (it) {
                        is OTGRegState.OnSuccess ->
                            regLauncher.launch(IntentSenderRequest.Builder(it.intentSender).build())
                        is OTGRegState.OnError -> toast(it.message)
                    }
                }
            }
        }
}