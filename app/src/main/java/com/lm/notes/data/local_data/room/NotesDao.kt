package com.lm.notes.data.local_data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("delete from notes where id = :id")
    fun deleteById(id: Int)

    @Delete
    fun delete(item: NoteModelRoom)

    @Query("select * from notes")
    fun getAllItems(): Flow<List<NoteModelRoom>>

    @Insert
    fun insert(item: NoteModelRoom): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: NoteModelRoom): Int

}
