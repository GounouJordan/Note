package com.jordy.note.data

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
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
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class NoteDataSourceTest {

    private lateinit var db: NoteDB
    private lateinit var noteDataSource: NoteDataSource

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = TestUtils.provideAppDb(context)
        noteDataSource = db.getNoteDataSource()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertUserIntoDb() = runTest {
        val date = Calendar.Builder().setDate(2023, 3, 12).build().time
        var entity = NoteEntity(
            id = 0,
            title = "",
            content = "some text",
            createdAt = date,
            modifiedAt = date
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
        val date = Calendar.Builder().setDate(2023, 3, 12).build().time
        for (i in 1..5) {
            entities.add(
                NoteEntity(
                    id = i.toLong(),
                    title = "Title $i",
                    content = "content $i",
                    createdAt = date,
                    modifiedAt = date
                )
            )
        }
        entities.forEach {
            noteDataSource.insert(it)
        }
        val pager = Pager(
            config = PagingConfig(pageSize = 10)
        ){
            noteDataSource.getAll()
        }
        pager.flow.test {
            assertEquals(entities as List<NoteEntity>, awaitItem())
            cancel()
        }
    }


    @Test
    fun deleteNote() = runTest {
        val date = Calendar.Builder().setDate(2023, 3, 12).build().time
        val note = NoteEntity(
            id = 0,
            title = "",
            content = "some text",
            createdAt = date,
            modifiedAt = date
        )

        val id = noteDataSource.insert(note)
        var result = noteDataSource.getNoteWithID(id)
        assert(result != null)
        noteDataSource.delete(result!!)
        result = noteDataSource.getNoteWithID(id)
        assert(result == null)
    }

}