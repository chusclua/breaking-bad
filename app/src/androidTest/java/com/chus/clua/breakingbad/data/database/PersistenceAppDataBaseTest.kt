package com.chus.clua.breakingbad.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.chus.clua.breakingbad.utils.model
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class PersistenceAppDataBaseTest {

    private lateinit var dao: PersistenceCharacterDao
    private lateinit var db: AppDataBase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(
            context,
            AppDataBase::class.java, "character_test.db"
        ).build()
        dao = db.persistenceDao()
        dao.insert(model)
    }

    @After
    fun tearDown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun when_insert_then_returns_a_Character_id() {
        val id = dao.insert(model)
        assertEquals(model.id, id)
    }

    @Test
    @Throws(Exception::class)
    fun when_findAll_then_returns_a_Character_List() {
        val list = dao.findAll()
        assertEquals(listOf(model), list)
    }

    @Test
    @Throws(Exception::class)
    fun when_contains_then_returns_true() {
        val contains = dao.contains(model.id)
        assertTrue(contains)
    }

    @Test
    @Throws(Exception::class)
    fun when_not_contains_then_returns_false() {
        val contains = dao.contains(10)
        assertFalse(contains)
    }

    @Test
    @Throws(Exception::class)
    fun when_delete_then_returns_number_of_rows_deleted() {
        val rows = dao.deleteById(model.id)
        assertEquals(1, rows)
    }
}