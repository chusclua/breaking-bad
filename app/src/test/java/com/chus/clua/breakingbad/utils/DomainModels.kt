package com.chus.clua.breakingbad.utils

import com.chus.clua.breakingbad.domain.models.Category
import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.domain.models.Status

val character = Character(
    1,
    "Walter White",
    "Heisenberg",
    "09-07-1958",
    listOf("High School Chemistry Teacher", "Meth King Pin"),
    "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
    Status.DECEASED,
    Category.BREAKING_BAD,
    "Bryan Cranston",
    listOf(1, 2, 3, 4, 5),
    listOf(),
    isFavorite = false
)