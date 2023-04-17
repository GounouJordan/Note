package com.jordy.note.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jordy.note.model.entities.NoteEntity
import com.jordy.note.repository.NoteRepository

class GetAllNoteUseCase(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(pageSize: Int = 15): Pager<Int, NoteEntity> =
        Pager(
            config = PagingConfig(pageSize = pageSize)
        ) {
            noteRepository.getAll()
        }
}