package ca.mcgill.science.tepid.data

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * This class is a port from the CUPPA system.
 * it is used in the ScreenSaver to encapsulate messages.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class MarqueeData (
        var title: String? = null, //the title to be displayed over the message
        var entry: List<String>? = null    //the message itself
)
