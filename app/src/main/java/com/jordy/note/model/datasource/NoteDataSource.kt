package com.jordy.note.model.datasource

import androidx.room.*
import com.jordy.note.model.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteEntity: NoteEntity): Long

    @Query("select * from notes")
    fun getAll(): Flow<List<NoteEntity>>

    @Update
    suspend fun update(noteEntity: NoteEntity)

    @Query("select * from notes where note_id =:id")
    suspend fun getNoteWithID(id: Long): NoteEntity?

    @Delete
    suspend fun delete(noteEntity: NoteEntity)
}