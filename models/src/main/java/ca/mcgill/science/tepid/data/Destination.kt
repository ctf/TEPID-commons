package ca.mcgill.science.tepid.data

import ca.mcgill.science.tepid.data.bindings.TepidDb
import ca.mcgill.science.tepid.data.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.data.bindings.TepidExtras
import ca.mcgill.science.tepid.data.bindings.TepidExtrasDelegate
import com.fasterxml.jackson.annotation.*
import java.util.*

/**
 * Created by Allan Wang on 2017-05-03.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class DestinationJson(
        var name: String = "",
        var protocol: String? = null,
        var username: String? = null,
        var password: String? = null,
        var path: String? = null,
        var domain: String? = null,
        var ticket: DestinationTicketJson? = null,
        var up: Boolean = false,
        var ppm: Int = 0
) : TepidDb by TepidDbDelegate(), TepidExtras by TepidExtrasDelegate()

//todo check duplicates (eg up)
data class Destination(
        val name: String, val protocol: String?, val username: String?, val path: String?,
        val domain: String?, val ticket: DestinationTicket?, val up: Boolean, val ppm: Int
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class DestinationTicketJson(
        var up: Boolean = false,
        var reason: String? = null,
        var user: User? = null, //todo change to userjson
        var reported: Date = Date()
)

data class DestinationTicket(
        val up: Boolean, val reason: String?, val user: User?, val reported: Date
)