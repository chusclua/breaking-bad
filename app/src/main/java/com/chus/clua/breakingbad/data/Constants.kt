package com.chus.clua.breakingbad.data

interface STATUS {
    companion object {
        const val DECEASED = "deceased"
        const val ALIVE = "alive"
        const val PRESUMED_DEAD = "presumed dead"
    }
}

interface CATEGORY {
    companion object {
        const val BOTH = "breaking bad, better call saul"
        const val BREAKING_BAD = "breaking bad"
        const val BETTER_CALL_SAUL = "better call saul"
    }
}