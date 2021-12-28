package com.chus.clua.breakingbad.data.models

data class CharacterRemoteModel(
    val char_id: Long?,
    val name: String?,
    val nickname: String?,
    val birthday: String?,
    val occupation: List<String>?,
    val img: String?,
    val status: String?,
    val category: String?,
    val portrayed: String?,
    val appearance: List<Int>?,
    val better_call_saul_appearance: List<Int>?
) : RemoteModel