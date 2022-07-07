package com.lm.notes.data.local_data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "notes")
class NoteModelRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: Long = 0,
    var note: String = "",
)


