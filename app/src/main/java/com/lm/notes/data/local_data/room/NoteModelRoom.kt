package com.lm.notes.data.local_data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "notes")
class NoteModelRoom(
    @PrimaryKey
    val id: String = "",
    val timestampCreate: Long = 0,
    val timestampChange: Long = 0,
    var transformMap: String = "",
    val text: String = "",
    val sizeX: Float = 0f,
    val sizeY: Float = 0f,
)


