package com.jordy.note.repository

import com.jordy.note.model.datasource.NoteDataSource
import com.jordy.note.model.entities.NoteEntity
import kotlinx.coroutines.flow.Flow


interface NoteRepository {
    fun getAll(): Flow<List<NoteEntity>>
    suspend fun insert(noteEntity: NoteEntity): Long
    suspend fun update(noteEntity: NoteEntity)
    suspend fun delete(noteEntity: NoteEntity)
    suspend fun getNoteWith(id: Long): NoteEntity?
}

class NoteRepositoryImpl(
    private val noteDataSource: NoteDataSource
): NoteRepository {
    override fun getAll(): Flow<List<NoteEntity>> = noteDataSource.getAll()

    override suspend fun insert(noteEntity: NoteEntity): Long = noteDataSource.insert(noteEntity)

    override suspend fun update(noteEntity: NoteEntity) = noteDataSource.update(noteEntity)

    override suspend fun delete(noteEntity: NoteEntity) = noteDataSource.delete(noteEntity)

    override suspend fun getNoteWith(id: Long): NoteEntity? = noteDataSource.getNoteWithID(id)
}