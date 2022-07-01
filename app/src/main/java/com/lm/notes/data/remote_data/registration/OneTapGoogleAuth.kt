package com.lm.notes.data.remote_data.registration

import android.app.Activity.RESULT_OK
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.lm.notes.utils.log
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface OneTapGoogleAuth {

    fun handleResultAndFBReg(result: ActivityResult): Flow<FBRegState>

    fun startAuth(): Flow<OTGRegState>

    class Base @Inject constructor(
        private val signInClient: SignInClient,
        private val fbAuth: FBAuth
    ) : OneTapGoogleAuth {

        override fun handleResultAndFBReg(result: ActivityResult) = callbackFlow {
            if (result.resultCode == RESULT_OK) {
                signInClient.getSignInCredentialFromIntent(result.data).apply {
                    val token = googleIdToken
                    val icon = profilePictureUri
                    token.log
                    if (token == null) trySendBlocking(FBRegState.OnError("null"))
                    else fbAuth.startAuthWithGoogleId(token)
                        .collect {
                            trySendBlocking(
                                when (it) {
                                    is FBRegState.OnSuccess -> FBRegState.OnSuccess(icon)
                                    is FBRegState.OnError -> FBRegState.OnError(it.message)
                                    is FBRegState.Loading -> FBRegState.Loading
                                }
                            )
                        }
                }
            }
            awaitClose()
        }.flowOn(IO)

        override fun startAuth() = callbackFlow {
            signInClient.beginSignIn(signInRequest)
                .addOnSuccessListener { result ->
                    trySendBlocking(OTGRegState.OnSuccess(result.pendingIntent.intentSender))
                }
                .addOnFailureListener {
                    trySendBlocking(OTGRegState.OnError(it.localizedMessage ?: "null"))
                }
            awaitClose()
        }.flowOn(IO)

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