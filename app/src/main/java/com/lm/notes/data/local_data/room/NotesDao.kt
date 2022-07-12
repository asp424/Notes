package com.lm.notes.data.local_data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("delete from notes where id = :id")
    fun deleteById(id: String)

    @Delete
    fun delete(item: NoteModelRoom)

    @Query("select * from notes")
    fun getAllItems(): List<NoteModelRoom>

    @Insert
    fun insert(item: NoteModelRoom)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: NoteModelRoom)

    @Query("SELECT * FROM notes WHERE id=:id ")
    fun getById(id: String): NoteModelRoom?

}
