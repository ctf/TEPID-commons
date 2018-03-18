package ca.mcgill.science.tepid.models.data

import java.util.*

enum class Season {
    // NOTE: Order matters! WINTER 2018 < SUMMER 2018 < FALL 2018
    WINTER,
    SUMMER, FALL;

    companion object {

        @JvmStatic
        fun fromMonth(month: Int) = when (month) {
            in Calendar.SEPTEMBER..Calendar.DECEMBER -> FALL
            in Calendar.JANUARY..Calendar.MAY -> WINTER
            else -> SUMMER
        }

        @JvmStatic
        operator fun invoke(name: String) = valueOf(name.toUpperCase(Locale.CANADA))
    }
}