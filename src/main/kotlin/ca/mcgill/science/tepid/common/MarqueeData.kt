package ca.mcgill.science.tepid.common

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * This class is a port from the CUPPA system.
 * it is used in the ScreenSaver to encapsulate messages.
 */
data class MarqueeData(
        var title: String? = null, //the title to be displayed over the message
        var entry: List<String>? = null    //the message itself
)
