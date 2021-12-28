package com.chus.clua.breakingbad.data.mappers

import com.chus.clua.breakingbad.domain.models.Category
import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.domain.models.Status
import com.chus.clua.breakingbad.utils.remoteModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterDomainMapperTest {

    private val mapper = CharacterDomainMapper()

    @Test
    fun `WHEN mapFromRemote THEN obtains a DomainModel`() {
        with(mapper.mapFromRemote(remoteModel)){
            assertEquals(1L, id)
            assertEquals("Walter White", name)
            assertEquals("Heisenberg", nickname)
            assertEquals("09-07-1958", birthday)
            assertEquals(listOf("High School Chemistry Teacher", "Meth King Pin"), occupation)
            assertEquals("https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg", img)
            assertEquals(Status.DECEASED, status)
            assertEquals(Category.BREAKING_BAD, category)
            assertEquals("Bryan Cranston", portrayed)
            assertEquals(listOf(1, 2, 3, 4, 5), appearance)
            assertEquals(listOf<Int>(), betterCallSaulAppearance)
        }
    }

    @Test
    fun `WHEN mapFromRemoteList THEN obtains a List of DomainModel`() {
        val mappedList = mapper.mapFromRemoteList(listOf(remoteModel))
        with(mappedList[0]){
            assertEquals(1L, id)
            assertEquals("Walter White", name)
            assertEquals("Heisenberg", nickname)
            assertEquals("09-07-1958", birthday)
            assertEquals(listOf("High School Chemistry Teacher", "Meth King Pin"), occupation)
            assertEquals("https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg", img)
            assertEquals(Status.DECEASED, status)
            assertEquals(Category.BREAKING_BAD, category)
            assertEquals("Bryan Cranston", portrayed)
            assertEquals(listOf(1, 2, 3, 4, 5), appearance)
            assertEquals(listOf<Int>(), betterCallSaulAppearance)
        }
    }

    @Test
    fun `WHEN mapFromRemoteList with null or empty THEN obtains a List of DomainModel`() {
        var mappedList = mapper.mapFromRemoteList(null)
        assertEquals(listOf<Character>(), mappedList)
        mappedList = mapper.mapFromRemoteList(listOf())
        assertEquals(listOf<Character>(), mappedList)
    }

    @Test
    fun `WHEN mapFromRemote with Status and Category null then obtains a DomainModel`() {
        with(mapper.mapFromRemote(remoteModel.copy(status = null, category = null))){
            assertEquals(Status.UNKNOWN, status)
            assertEquals(Category.UNKNOWN, category)
        }
    }
}