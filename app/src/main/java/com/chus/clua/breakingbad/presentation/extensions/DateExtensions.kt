package com.chus.clua.breakingbad.presentation.extensions

import java.text.SimpleDateFormat
import java.util.*

private const val PATTERN = "dd-MM-yyyy"

fun String?.calculateAge(
    today: Calendar = Calendar.getInstance(),
    pattern: String = PATTERN
): String {
    this?.let {
        val date = it toDate pattern
        val dob: Calendar = Calendar.getInstance()
        date?.time?.let { time -> dob.timeInMillis = time } ?: run { return "Unknown" }
        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) age--
        return if (age.isPositive()) age.toString() else "Unknown"
    } ?: run { return "Unknown" }
}

infix fun String?.toDate(pattern: String): Date? {
    return try {
        SimpleDateFormat(pattern, Locale.ROOT).parse(this)
    } catch (ex: Exception) {
        null
    }

}