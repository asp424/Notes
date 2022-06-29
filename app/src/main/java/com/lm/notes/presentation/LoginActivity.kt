package com.lm.notes.presentation

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity
import com.lm.notes.data.remote_data.registration.FBRegState
import com.lm.notes.data.remote_data.registration.OTGRegState
import com.lm.notes.di.dagger.DaggerRegComponent
import com.lm.notes.utils.log
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity() {

    private val regComponent by lazy { DaggerRegComponent.builder()
        .signInClient(Identity.getSignInClient(this)).create()
    }

    private val regLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult())
        { result ->
            lifecycleScope.launch {
                regComponent.oTGAuth().handleResultAndFBReg(result).collect {
                    when (it) {
                        is FBRegState.OnSuccess -> { it.uid.log; finish() }
                        is FBRegState.OnError -> { it.message.log; finish() }
                        is FBRegState.Loading -> "loading".log
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            regComponent.oTGAuth().startAuth().collect {
                when (it) {
                    is OTGRegState.OnSuccess -> regLauncher.launch(
                        IntentSenderRequest.Builder(it.intentSender).build()
                    )
                    is OTGRegState.OnError -> it.message.log
                }
            }
        }
    }
}