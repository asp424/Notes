package com.lm.notes.data.remote_data.registration

import android.app.Activity.RESULT_OK
import android.net.Uri
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.CommonStatusCodes.CANCELED
import com.google.android.gms.common.api.CommonStatusCodes.NETWORK_ERROR
import com.lm.notes.utils.log
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

interface OneTapGoogleAuth {

    fun handleResultAndFBReg(result: ActivityResult): Flow<FBRegState>

    fun startAuth(onResult: (OTGRegState) -> Unit)

    class Base @Inject constructor(
        private val signInClient: SignInClient,
        private val fbAuth: FBAuth
    ) : OneTapGoogleAuth {

        override fun handleResultAndFBReg(result: ActivityResult) = callbackFlow {
            if (result.resultCode == RESULT_OK) {
                try {
                    signInClient.getSignInCredentialFromIntent(result.data).apply {
                        val token = googleIdToken
                        if (token.isNullOrEmpty()) trySendBlocking(FBRegState.OnError("null"))
                        else launch {
                            fbAuth.startAuthWithGoogleId(token).collect {
                                trySendBlocking(
                                    when (it) {
                                        is FBRegState.OnSuccess -> FBRegState.OnSuccess(
                                            profilePictureUri
                                        )
                                        is FBRegState.OnError -> FBRegState.OnError(it.message)
                                        is FBRegState.OnClose -> FBRegState.OnClose("close")
                                    }
                                )
                            }
                        }
                    }
                } catch(e: ApiException){
                    trySendBlocking(
                        when (e.statusCode) {
                            CANCELED ->  FBRegState.OnClose(e.message?: "cancelled")
                            NETWORK_ERROR ->  FBRegState.OnClose(e.message?: "network error")
                            else -> FBRegState.OnError(e.message?: "error")
                        }
                    )
                }
            }
            awaitClose()
        }.flowOn(IO)

        override fun startAuth(onResult: (OTGRegState) -> Unit) {
            signInClient.beginSignIn(signInRequest)
                .addOnSuccessListener { result ->
                    onResult(OTGRegState.OnSuccess(result.pendingIntent.intentSender))
                }
                .addOnFailureListener {
                    onResult(OTGRegState.OnError(it.localizedMessage ?: "null"))
                }
        }

        private val requestOptions by lazy {
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(webClientId)
                .setFilterByAuthorizedAccounts(false)
                .build()
        }

        private val signInRequest by lazy {
            BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(requestOptions)
                .build()
        }

        private val webClientId by lazy {
            "178825274872-mlc74lvnna46bd6jib95suu6mkecdk0k.apps.googleusercontent.com"
        }
    }
}