package com.lm.notes.data.remote_data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ServerValue
import com.lm.notes.data.remote_data.firebase.NotesHandler.Base.Companion.NOTE
import com.lm.notes.utils.log
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface FirebaseHandler {

    fun runListener(node: String, mode: ListenerMode): Flow<FBLoadStates>

    suspend fun <T> successFlow(task: Task<T>, scope: ProducerScope<FBLoadStates>)

    fun <T> runTask(task: Task<T>): Flow<FBLoadStates>

    suspend fun <T> runSuspendCoroutine(task: Task<T>): Flow<FBLoadStates>

    fun saveString(value: String, node: String, key: String): Flow<FBLoadStates>

    class Base @Inject constructor(
        private val firebaseAuth: FirebaseAuth,
        private val fireBaseDatabase: DatabaseReference,
        private val valueListener: ValueListener,
        private val childListener: ChildListener
    ) : FirebaseHandler {

        override fun <T> runTask(task: Task<T>): Flow<FBLoadStates> =
            callbackFlow { successFlow(task, this) }.flowOn(IO)

        override suspend fun <T> successFlow(task: Task<T>, scope: ProducerScope<FBLoadStates>) =
            with(scope) {
                with(task) {
                    addOnSuccessListener(Executors.newSingleThreadExecutor())
                    { trySendBlocking(FBLoadStates.Success(it)) }
                    addOnCompleteListener(Executors.newSingleThreadExecutor())
                    { trySendBlocking(FBLoadStates.Complete) }
                    addOnCanceledListener { trySendBlocking(FBLoadStates.Cancelled) }
                    addOnFailureListener {
                        trySendBlocking(FBLoadStates.Failure(it.message ?: "Failure"))
                    }
                    awaitClose { trySendBlocking(FBLoadStates.EndLoading) }
                }
            }

        override suspend fun <T> runSuspendCoroutine(task: Task<T>) =
            suspendCoroutine<Flow<FBLoadStates>> { it.resume(runTask(task)) }

        override fun runListener(node: String, mode: ListenerMode) = callbackFlow {
            node.path.apply {
                when (mode) {
                    ListenerMode.REALTIME -> valueListener.listener(this@callbackFlow)
                        .also { listener ->
                            addValueEventListener(listener)
                            awaitClose { removeEventListener(listener); "endC".log }
                        }
                    ListenerMode.SINGLE -> valueListener.listener(this@callbackFlow)
                        .also { listener ->
                            addListenerForSingleValueEvent(listener)
                            awaitClose { removeEventListener(listener); "end".log }
                        }
                    ListenerMode.CHILD -> childListener.listener(this@callbackFlow)
                        .also { listener ->
                            addChildEventListener(listener)
                            awaitClose { removeEventListener(listener) }
                        }
                }
            }
        }.flowOn(IO)

        override fun saveString(value: String, node: String, key: String) =
            with(id) {
                runTask(
                    node.path.child(this).updateChildren(
                        mapOf(key to value, TIMESTAMP to timestamp, NOTE_ID to this)
                    )
                )
            }

        private val String.path
            get() = fireBaseDatabase
                .child(firebaseAuth.currentUser?.uid.toString()).child(this)

        private val timestamp get() = ServerValue.TIMESTAMP

        private val id get() = fireBaseDatabase.push().key.toString()

        companion object {
            const val TIMESTAMP = "timestamp"
            const val NOTE_ID = "id"
        }
    }
}





