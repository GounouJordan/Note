package com.jordy.note.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey
    @ColumnInfo(name = "note_id")
    val id: Long,
    @ColumnInfo(name = "note_title")
    val title: String,
    @ColumnInfo(name = "note_content")
    val content: String,
    @ColumnInfo(name = "note_created_at")
    val createdAt: Date,
    @ColumnInfo(name = "note_modified_at")
    val modifiedAt: Date
)