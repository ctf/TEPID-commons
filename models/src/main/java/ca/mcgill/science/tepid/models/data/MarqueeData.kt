package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidJackson

/**
 * This class is a port from the CUPPA system.
 * it is used in the ScreenSaver to encapsulate messages.
 */
data class MarqueeData(
        var title: String? = null, //the title to be displayed over the message
        var entry: List<String> = emptyList()    //the message itself
) : TepidJackson