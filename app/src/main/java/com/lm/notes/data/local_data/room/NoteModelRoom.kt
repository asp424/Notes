package com.lm.notes.data.local_data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
class NoteModelRoom(
    @PrimaryKey
    val id: String = "",
    val timestampCreate: Long = 0,
    val timestampChange: Long = 0,
    var underlinedMap: String = "",
    val text: String = "",
    val header: String = ""
)


