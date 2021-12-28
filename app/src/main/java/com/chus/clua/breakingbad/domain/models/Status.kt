package com.chus.clua.breakingbad.domain.models

enum class Status(val commonName: String) {
    DECEASED("Deceased"),
    ALIVE("Alive"),
    PRESUMED_DEAD("Presumed dead"),
    UNKNOWN("Status unknown")
}