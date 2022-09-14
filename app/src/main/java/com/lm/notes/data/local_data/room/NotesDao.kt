package com.lm.notes.data.local_data.room

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.*
import com.lm.notes.data.models.NoteModel

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getNotesListPaged(): DataSource.Factory<Int, NoteModelRoom>

    @Query("SELECT * FROM notes")
    fun getList(): PagingSource<Int, NoteModelRoom>

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

    @Query("SELECT * FROM notes")
    fun getAllImages(): PagingSource<Int, NoteModelRoom>
}
