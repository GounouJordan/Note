package com.jordy.note.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.jordy.note.model.data.Note
import com.jordy.note.model.data.noteFromNoteEntity
import com.jordy.note.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllNoteUseCase(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(pageSize: Int = 15): Flow<PagingData<Note>> =
        Pager(
            config = PagingConfig(pageSize = pageSize)
        ) {
            noteRepository.getAll()
        }.flow.map { pagingData ->
            pagingData.map {
                noteFromNoteEntity(it)
            }
        }
}