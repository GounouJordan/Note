package com.jordy.note.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jordy.note.model.datasource.NoteDataSource
import com.jordy.note.model.entities.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDB: RoomDatabase() {
    abstract fun getNoteDataSource(): NoteDataSource

    companion object{
        private var Instance: NoteDB? = null
        fun getInstance(appContext: Context): NoteDB{
            if (Instance == null) {
                Instance = Room.inMemoryDatabaseBuilder(
                        context = appContext,
                        NoteDB::class.java
                    ).build()
            }
            return Instance!!
        }
    }
}