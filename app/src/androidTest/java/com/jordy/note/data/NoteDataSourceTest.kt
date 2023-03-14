package com.jordy.note.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.jordy.note.db.NoteDB
import com.jordy.note.model.datasource.NoteDataSource
import com.jordy.note.model.entities.NoteEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class NoteDataSourceTest {

    private lateinit var db: NoteDB
    private lateinit var noteDataSource: NoteDataSource

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NoteDB::class.java).build()
        noteDataSource = db.getNoteDataSource()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertUserIntoDb() = runTest {
        var entity = NoteEntity(
            id = 0,
            title = "",
            content = "some text",
            createdAt = 125,
            modifiedAt = 125
        )
        val id = noteDataSource.insert(entity)
        entity = entity.copy(id = id)
        val result = noteDataSource.getNoteWithID(id)
        assert(result != null)
        assert(result == entity)
    }

    @Test
    fun getAllNotes() = runTest {
        val entities = mutableListOf<NoteEntity>()
        for (i in 1..5) {
            entities.add(
                NoteEntity(
                    id = i.toLong(),
                    title = "Title $i",
                    content = "content $i",
                    createdAt = 125 + i * 1_000L,
                    modifiedAt = 125 + i * 1_000L
                )
            )
        }
        entities.forEach {
            noteDataSource.insert(it)
        }

        noteDataSource.getAll().test {
            assertEquals(entities as List<NoteEntity>, awaitItem())
            cancel()
        }
    }


    @Test
    fun deleteNote() = runTest {
        val note = NoteEntity(
            id = 0,
            title = "",
            content = "some text",
            createdAt = 125,
            modifiedAt = 125
        )

        val id = noteDataSource.insert(note)
        var result = noteDataSource.getNoteWithID(id)
        assert(result != null)
        noteDataSource.delete(result!!)
        result = noteDataSource.getNoteWithID(id)
        assert(result == null)
    }

}