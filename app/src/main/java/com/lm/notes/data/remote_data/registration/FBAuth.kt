package com.lm.notes.data.remote_data.registration

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface FBAuth {

    fun startAuthWithGoogleIdToken(googleIdToken: String): Flow<FBRegState>

    class Base @Inject constructor(
        private val firebaseAuth: FirebaseAuth
    ): FBAuth {

        override fun startAuthWithGoogleIdToken(googleIdToken: String)
        = callbackFlow{
            val credential = GoogleAuthProvider.getCredential(googleIdToken, null)
           firebaseAuth.signInWithCredential(credential)
               .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user.apply {
                        if (this == null) trySendBlocking(FBRegState.OnError("null"))
                         else trySendBlocking(FBRegState.OnSuccess(uid))
                    }
                } else {
                    task.exception.apply {
                        if (this == null) trySendBlocking(FBRegState.OnError("null"))
                         else trySendBlocking(FBRegState.OnError(message?: "null"))
                    }
                }
            }
            awaitClose()
        }.flowOn(IO)
    }
}