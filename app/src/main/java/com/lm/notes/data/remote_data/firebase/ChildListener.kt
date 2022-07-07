package com.lm.notes.data.remote_data.firebase

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.trySendBlocking
import javax.inject.Inject

interface ChildListener {
    fun listener(scope: ProducerScope<FBLoadStates>): ChildEventListener

    fun snapshot(
        scope: ProducerScope<FBLoadStates>,
        snapshot: Any, flag: Int
    ): ChannelResult<Unit>

    class Base @Inject constructor() : ChildListener {
        override fun listener(scope: ProducerScope<FBLoadStates>) =
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
            scope: ProducerScope<FBLoadStates>,
            snapshot: Any, flag: Int
        ) = scope.trySendBlocking(
            when (snapshot) {
                is DataSnapshot -> if (flag == 0) FBLoadStates.Success(snapshot)
                else FBLoadStates.Update(snapshot)
                is DatabaseError -> FBLoadStates.Failure(snapshot)
                else -> FBLoadStates.Loading
            }
        )
    }
}