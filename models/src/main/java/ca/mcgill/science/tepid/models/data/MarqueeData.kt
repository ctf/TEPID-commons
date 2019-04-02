package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import javax.persistence.*

/**
 * Used in ScreenSaver to encapsulate messages.
 * Each marquee object has a title and a list of entries which will display under that title
 */
@Entity
data class MarqueeData(
        var title: String? = null, //the title to be displayed over the message
        @Access(AccessType.FIELD)
        @ElementCollection(fetch = FetchType.EAGER)
        var entry: List<String> = emptyList()    //the message itself
) : TepidDb by TepidDbDelegate()