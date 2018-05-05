package ca.mcgill.science.tepid.models.enums

import java.util.*

enum class PrintError(val msg: String) {
    INSUFFICIENT_QUOTA("Insufficient Quota"),
    COLOR_DISABLED("Color Disabled"),
    INVALID_DESTINATION("Invalid Destination"),
    GENERIC("Error");

    companion object {
        private val mapper = values().map { it.name.toLowerCase(Locale.CANADA) to it }.toMap()
        fun fromMessage(msg: String) = mapper.getOrDefault(msg.toLowerCase(Locale.CANADA), GENERIC)
    }
}