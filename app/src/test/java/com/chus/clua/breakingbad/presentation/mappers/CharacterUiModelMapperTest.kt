package com.chus.clua.breakingbad.presentation.mappers

import com.chus.clua.breakingbad.utils.character
import org.junit.Assert.*
import org.junit.Test

class CharacterUiModelMapperTest {

    private val mapper = CharacterUiModelMapper()

    @Test
    fun `WHEN mapFromDomain THEN obtains a UiModel`() {
        with(mapper.mapFromDomain(character)) {
            assertEquals(1L, id)
            assertEquals("Walter White", name)
            assertEquals("Heisenberg", nickname)
            assertEquals("63 age", ages)
            assertEquals("High School Chemistry Teacher, Meth King Pin", occupation)
            assertEquals("https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg", img)
            assertEquals("Deceased", status)
            assertEquals("Breaking bad", category)
            assertEquals("Bryan Cranston", portrayed)
            assertEquals("1, 2, 3, 4, 5", appearance)
            assertEquals("", betterCallSaulAppearance)
            assertFalse(isFavorite)
        }
    }

    @Test
    fun `WHEN mapFromDomain with invalid date THEN obtains a UiModel`() {
        var character = character.copy(birthday = null)
        with(mapper.mapFromDomain(character)) {
            assertEquals("Unknown age", ages)
        }
        character = character.copy(birthday = "XX-XX-XXXX")
        with(mapper.mapFromDomain(character)) {
            assertEquals("Unknown age", ages)
        }
    }

}