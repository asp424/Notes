package com.lm.notes.data.sources

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ServerValue
import com.lm.notes.data.remote_data.RemoteLoadStates
import com.lm.notes.data.remote_data.firebase.ChildListener
import com.lm.notes.data.remote_data.firebase.ListenerMode
import com.lm.notes.data.remote_data.firebase.ValueListener
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface FirebaseSource {

    fun runListener(node: String, mode: ListenerMode): Flow<RemoteLoadStates>

    suspend fun <T> successFlow(task: Task<T>, scope: ProducerScope<RemoteLoadStates>): Task<T>

    fun <T> runTask(task: Task<T>): Flow<RemoteLoadStates>

    suspend fun <T> runSuspendCoroutine(task: Task<T>): Flow<RemoteLoadStates>

    fun saveString(value: String, node: String, key: String): Flow<RemoteLoadStates>

    fun saveStringById(
        value: String,
        node: String,
        key: String,
        id: String,
        sizeX: Float,
        sizeY: Float,
        timestamp: Long
    )

    val randomId: String

    val isAuth: Boolean

    class Base @Inject constructor(
        private val firebaseAuth: FirebaseAuth,
        private val fireBaseDatabase: DatabaseReference,
        private val valueListener: ValueListener,
        private val childListener: ChildListener
    ) : FirebaseSource {

        override fun <T> runTask(task: Task<T>): Flow<RemoteLoadStates> =
            callbackFlow { successFlow(task, this) }.flowOn(IO)

        override suspend fun <T> successFlow(
            task: Task<T>,
            scope: ProducerScope<RemoteLoadStates>
        ) =
            with(scope) {
                task.apply {
                    addOnSuccessListener(Executors.newSingleThreadExecutor())
                    { trySendBlocking(RemoteLoadStates.Success(it)) }
                    addOnCompleteListener(Executors.newSingleThreadExecutor())
                    { trySendBlocking(RemoteLoadStates.Complete) }
                    addOnCanceledListener { trySendBlocking(RemoteLoadStates.Cancelled) }
                    addOnFailureListener { trySendBlocking(RemoteLoadStates.Failure(it)) }
                    awaitClose { trySendBlocking(RemoteLoadStates.EndLoading) }
                }
            }

        override suspend fun <T> runSuspendCoroutine(task: Task<T>) =
            suspendCoroutine<Flow<RemoteLoadStates>> { it.resume(runTask(task)) }

        override fun runListener(node: String, mode: ListenerMode) = callbackFlow {
            node.path.apply {
                when (mode) {
                    ListenerMode.REALTIME -> valueListener.listener(this@callbackFlow)
                        .also { listener ->
                            addValueEventListener(listener)
                            awaitClose { removeEventListener(listener) }
                        }
                    ListenerMode.SINGLE -> valueListener.listener(this@callbackFlow)
                        .also { listener ->
                            addListenerForSingleValueEvent(listener)
                            awaitClose { removeEventListener(listener) }
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
            with(randomId) {
                runTask(
                    node.path.child(this).updateChildren(
                        mapOf(key to value, TIMESTAMP to actualTimestamp, ID to this)
                    )
                )
            }

        override fun saveStringById(
            value: String,
            node: String,
            key: String,
            id: String,
            sizeX: Float,
            sizeY: Float,
            timestamp: Long
        ) {
            if (isAuth) runTask(
                node.path.child(id).updateChildren(
                    mapOf(
                        key to value,
                        TIMESTAMP to timestamp,
                        ID to id,
                        "sizeX" to sizeX,
                        "sizeY" to sizeY
                    )
                )
            )
        }

        private val String.path
            get() = fireBaseDatabase
                .child(firebaseAuth.currentUser?.uid.toString()).child(this)

        private val actualTimestamp get() = ServerValue.TIMESTAMP

        override val randomId get() = fireBaseDatabase.push().key.toString()

        override val isAuth: Boolean get() = firebaseAuth.currentUser?.uid != null

        companion object {
            const val TIMESTAMP = "timestamp"
            const val ID = "id"
            const val NOTES = "notes"
            const val TEXT = "text"
        }
    }
}





