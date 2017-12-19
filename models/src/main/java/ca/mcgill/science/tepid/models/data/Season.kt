package ca.mcgill.science.tepid.models.data

import java.util.*

enum class Season {
    FALL, WINTER, SUMMER;

    companion object {

        fun fromMonth(month: Int) = when (month) {
            in Calendar.SEPTEMBER .. Calendar.DECEMBER -> FALL
            in Calendar.JANUARY .. Calendar.MAY -> WINTER
            else -> SUMMER
        }

        operator fun invoke(name: String) = valueOf(name.toUpperCase())
    }
}