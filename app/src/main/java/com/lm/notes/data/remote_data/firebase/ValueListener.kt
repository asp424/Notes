package com.lm.notes.data.remote_data.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.lm.notes.data.remote_data.RemoteLoadStates
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.trySendBlocking
import javax.inject.Inject

interface ValueListener {
    fun listener(scope: ProducerScope<RemoteLoadStates>): ValueEventListener

    fun snapshot(scope: ProducerScope<RemoteLoadStates>,
                 snapshot: Any): ChannelResult<Unit>

    class Base @Inject constructor() : ValueListener {
        override fun listener(scope: ProducerScope<RemoteLoadStates>) =
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) { snapshot(scope, snapshot) }
                override fun onCancelled(error: DatabaseError) { snapshot(scope, error) }
            }

        override fun snapshot(scope: ProducerScope<RemoteLoadStates>, snapshot: Any)
        = scope.trySendBlocking(
            when (snapshot) {
                is DataSnapshot -> RemoteLoadStates.Success(snapshot)
                is DatabaseError -> RemoteLoadStates.Failure(snapshot)
                else -> RemoteLoadStates.Loading
            }
        )
    }
}