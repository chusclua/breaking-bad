package com.chus.clua.breakingbad.presentation.mappers

import com.chus.clua.breakingbad.presentation.models.CharacterListUiModel
import com.chus.clua.breakingbad.utils.character
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterListUiModelMapperTest {

    private val mapper = CharacterListUiModelMapper()

    @Test
    fun `WHEN mapFromDomain THEN obtains a UiModel`() {
        with(mapper.mapFromDomain(character)) {
            assertEquals(1L, id)
            assertEquals("Walter White", name)
            assertEquals("https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg", img)
        }
    }

    @Test
    fun `WHEN mapFromDomainList THEN obtains a UiModel`() {
        val mappedList = mapper.mapFromDomainList(listOf(character))
        with(mappedList[0]) {
            assertEquals(1L, id)
            assertEquals("Walter White", name)
            assertEquals("https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg", img)
        }
    }

    @Test
    fun `WHEN mapFromDomainList with null or empty THEN obtains a List of UiModel`() {
        var mappedList = mapper.mapFromDomainList(null)
        assertEquals(listOf<CharacterListUiModel>(), mappedList)
        mappedList = mapper.mapFromDomainList(listOf())
        assertEquals(listOf<CharacterListUiModel>(), mappedList)
    }

}