package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidJackson
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.concurrent.TimeUnit

data class About(val debug: Boolean, val ldapEnabled: Boolean,
                 val startTimestamp: Long = -1,
                 val startTime: String = "",
                 val hash: String = "", val warnings: List<String> = emptyList()) : TepidJackson {

    val uptime: String
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        get() {
            val up = System.currentTimeMillis() - startTimestamp
            val hours = TimeUnit.MILLISECONDS.toHours(up)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(up) - TimeUnit.HOURS.toMinutes(hours)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(up) - TimeUnit.MINUTES.toSeconds(minutes)
            return String.format("%02d hours, %02d min, %02d sec", hours, minutes, seconds)
        }

}