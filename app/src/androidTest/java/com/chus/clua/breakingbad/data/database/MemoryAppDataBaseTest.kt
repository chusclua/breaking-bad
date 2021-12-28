package com.chus.clua.breakingbad.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.chus.clua.breakingbad.domain.models.Category
import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.utils.model
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MemoryAppDataBaseTest {

    private lateinit var dao: MemoryCharacterDao
    private lateinit var db: AppDataBase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        dao = db.memoryDao()
        dao.insertAll(listOf(model))
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun when_findById_then_returns_a_Character() {
        val character = dao.findById(model.id)
        assertEquals(model, character)
    }

    @Test
    @Throws(Exception::class)
    fun when_findById_then_returns_null() {
        val character = dao.findById(10)
        assertEquals(null, character)
    }

    @Test
    @Throws(Exception::class)
    fun when_findAllByCategories_then_returns_a_list_of_characters() {
        val characters = dao.findAllByCategories(Category.BREAKING_BAD.name)
        assertEquals(listOf(model), characters)
    }

    @Test
    @Throws(Exception::class)
    fun when_findAllByCategories_then_returns_an_empty_list_of_characters() {
        val characters = dao.findAllByCategories(Category.BETTER_CALL_SAUL.name)
        assertEquals(listOf<Character>(), characters)
    }

    @Test
    @Throws(Exception::class)
    fun when_insertAll_then_returns_a_list_of_ids() {
        val ids = dao.insertAll(listOf(model))
        assertEquals(101, ids[0])
    }

    @Test
    @Throws(Exception::class)
    fun when_contains_then_returns_true() {
        val contains = dao.containsCategory(Category.BREAKING_BAD.name)
        assertTrue(contains)
    }

    @Test
    @Throws(Exception::class)
    fun when_not_contains_then_returns_false() {
        val contains = dao.containsCategory(Category.BETTER_CALL_SAUL.name)
        assertFalse(contains)
    }
}