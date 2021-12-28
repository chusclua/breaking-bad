package com.chus.clua.breakingbad.presentation.mappers

import com.chus.clua.breakingbad.utils.character
import org.junit.Assert.*
import org.junit.Test

class FavoriteCharacterUiModelMapperTest {

    private val mapper = FavoriteCharacterUiModelMapper()

    @Test
    fun `WHEN mapFromDomain THEN obtains a UiModel`() {
        val uiModel = mapper.mapFromDomain(character)
        with(uiModel) {
            assertEquals(1L, id)
            assertEquals("https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg", img)
            assertEquals("Walter White", name)
            assertEquals("Heisenberg", nickname)
            assertEquals("High School Chemistry Teacher, Meth King Pin", occupation)
            assertEquals("Breaking bad", category)
        }
    }

    @Test
    fun `WHEN mapFromDomainList THEN obtains a UiModel`() {
        val uiModelList = mapper.mapFromDomainList(listOf(character))
        with(uiModelList[0]) {
            assertEquals(1L, id)
            assertEquals("https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg", img)
            assertEquals("Walter White", name)
            assertEquals("Heisenberg", nickname)
            assertEquals("High School Chemistry Teacher, Meth King Pin", occupation)
            assertEquals("Breaking bad", category)
        }
    }

}