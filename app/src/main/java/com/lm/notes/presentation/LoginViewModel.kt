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
import com.lm.notes.data.remote_data.firebase.FBRegStates
import com.lm.notes.data.remote_data.one_tap_google.OTGRegState
import com.lm.notes.data.remote_data.one_tap_google.OneTapGoogleAuth
import com.lm.notes.utils.longToast
import com.lm.notes.utils.setIconUri
import com.lm.notes.utils.setIsAuth
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                delay(20000L); longToast("Authorize error, try later")
                startMainActivity
            }
            oneTapGoogleAuth.handleResultAndFBReg(result).collect {
                when (it) {
                    is FBRegStates.OnSuccess -> {
                        sPreferences.saveIconUri(it.iconUri?.apply { setIconUri })

                        withContext(Main){
                            longToast("Вход выполнен")
                            true.setIsAuth
                        }
                    }
                    is FBRegStates.OnError -> {
                        withContext(Main){
                            longToast(it.message)
                        }
                    }
                    is FBRegStates.OnClose -> { delay(1000) }
                }
                startMainActivity
            }
        }

    private fun LoginActivity.startOTGAuth(
        regLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) = oneTapGoogleAuth.startAuth {
        when (it) {
            is OTGRegState.OnSuccess ->
                regLauncher.launch(IntentSenderRequest.Builder(it.intentSender).build())
            is OTGRegState.OnError -> {
                longToast(it.message); startMainActivity
            }
        }
    }
}