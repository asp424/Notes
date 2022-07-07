package com.lm.notes.data.remote_data.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.trySendBlocking
import javax.inject.Inject

interface ValueListener {
    fun listener(scope: ProducerScope<FBLoadStates>): ValueEventListener

    fun snapshot(scope: ProducerScope<FBLoadStates>,
                 snapshot: Any): ChannelResult<Unit>

    class Base @Inject constructor() : ValueListener {
        override fun listener(scope: ProducerScope<FBLoadStates>) =
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) { snapshot(scope, snapshot) }
                override fun onCancelled(error: DatabaseError) { snapshot(scope, error) }
            }

        override fun snapshot(scope: ProducerScope<FBLoadStates>, snapshot: Any)
        = scope.trySendBlocking(
            when (snapshot) {
                is DataSnapshot -> FBLoadStates.Success(snapshot)
                is DatabaseError -> FBLoadStates.Failure(snapshot)
                else -> FBLoadStates.Loading
            }
        )
    }
}