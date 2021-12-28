package com.chus.clua.breakingbad.domain.models

enum class Category(val commonName: String) {
    BREAKING_BAD("Breaking bad"),
    BETTER_CALL_SAUL("Better call Saul"),
    BREAKING_BETTER("Breaking bad, Better call Saul"),
    UNKNOWN("Unknown")
}