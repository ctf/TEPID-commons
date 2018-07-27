package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidJackson

/**
 * Used in ScreenSaver to encapsulate messages.
 * Each marquee object has a title and a list of entries which will display under that title
 */
data class MarqueeData(
        var title: String? = null, //the title to be displayed over the message
        var entry: List<String> = emptyList()    //the message itself
) : TepidJackson