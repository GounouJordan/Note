package com.jordy.note.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jordy.note.model.datasource.NoteDataSource
import com.jordy.note.model.entities.NoteEntity
import com.jordy.note.model.entities.converters.DateConverter

@Database(
    entities = [NoteEntity::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class NoteDB: RoomDatabase() {
    abstract fun getNoteDataSource(): NoteDataSource

    companion object{
        private var Instance: NoteDB? = null
        fun getInstance(appContext: Context): NoteDB{
            if (Instance == null) {
                Instance = Room.inMemoryDatabaseBuilder(
                        context = appContext,
                        NoteDB::class.java
                    ).addTypeConverter(DateConverter())
                    .build()
            }
            return Instance!!
        }
    }
}