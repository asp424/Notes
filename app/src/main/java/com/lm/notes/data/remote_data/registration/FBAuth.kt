package com.lm.notes.data.remote_data.registration

import android.net.Uri
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

    fun startAuthWithGoogleId(googleIdToken: String): Flow<FBRegState>

    class Base @Inject constructor(
        private val firebaseAuth: FirebaseAuth
    ) : FBAuth {

        override fun startAuthWithGoogleId(googleIdToken: String) = callbackFlow {
            val credential = GoogleAuthProvider.getCredential(googleIdToken, null)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    trySendBlocking(
                    if (task.isSuccessful) FBRegState.OnSuccess(Uri.EMPTY)
                    else FBRegState.OnError(task.exception?.message ?: "null")
                    )
                }.addOnFailureListener { trySendBlocking(FBRegState.OnError(it.message ?: "null")) }
                .addOnCanceledListener { trySendBlocking(FBRegState.OnError("cancelled")) }
            awaitClose()
        }.flowOn(IO)
    }
}