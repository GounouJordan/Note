package com.jordy.note.model.datasource

import androidx.paging.PagingSource
import androidx.room.*
import com.jordy.note.model.entities.NoteEntity

@Dao
interface NoteDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteEntity: NoteEntity): Long

    @Query("select * from notes")
    fun getAll(): PagingSource<Int, NoteEntity>

    @Update
    suspend fun update(noteEntity: NoteEntity)

    @Query("select * from notes where note_id =:id")
    suspend fun getNoteWithID(id: Long): NoteEntity?

    @Delete
    suspend fun delete(noteEntity: NoteEntity)
}