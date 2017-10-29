package ca.mcgill.science.tepid.models

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * This class is a port from the CUPPA system.
 * it is used in the ScreenSaver to encapsulate messages.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class MarqueeDataJson(
        var title: String? = null, //the title to be displayed over the message
        var entry: List<String>? = null    //the message itself
)

data class MarqueeData(val title: String?, val entry: List<String>?)
