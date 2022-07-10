package com.lm.notes.data.remote_data.firebase

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.lm.notes.data.remote_data.RemoteLoadStates
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.trySendBlocking
import javax.inject.Inject

interface ChildListener {
    fun listener(scope: ProducerScope<RemoteLoadStates>): ChildEventListener

    fun snapshot(
        scope: ProducerScope<RemoteLoadStates>,
        snapshot: Any, flag: Int
    ): ChannelResult<Unit>

    class Base @Inject constructor() : ChildListener {
        override fun listener(scope: ProducerScope<RemoteLoadStates>) =
            object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    snapshot(scope, snapshot,  0)
                }

                override fun onChildChanged(
                    snapshot: DataSnapshot,
                    previousChildName: String?
                ) {
                    snapshot(scope, snapshot, 1)
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {}
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onCancelled(error: DatabaseError) {
                    snapshot(scope, error, 0)
                }
            }

        override fun snapshot(
            scope: ProducerScope<RemoteLoadStates>,
            snapshot: Any, flag: Int
        ) = scope.trySendBlocking(
            when (snapshot) {
                is DataSnapshot -> if (flag == 0) RemoteLoadStates.Success(snapshot)
                else RemoteLoadStates.Update(snapshot)
                is DatabaseError -> RemoteLoadStates.Failure(snapshot)
                else -> RemoteLoadStates.Loading
            }
        )
    }
}