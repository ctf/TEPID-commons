package ca.mcgill.science.tepid.common

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * This class is a port from the CUPPA system.
 * it is used in the ScreenSaver to encapsulate messages.
 */
@JsonIgnoreProperties("_rev", "_id", "type")
data class MarqueeData(
        @JsonProperty("title")
        var title: String? = null, //the title to be displayed over the message
        @JsonProperty("entry")
        var entry: List<String>? = null    //the message itself
)
