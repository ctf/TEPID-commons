package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.models.bindings.TepidExtras
import ca.mcgill.science.tepid.models.bindings.TepidExtrasDelegate
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

/**
 * Created by Allan Wang on 2017-05-03.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Destination(
        var name: String = "",
        var protocol: String? = null,
        var username: String? = null,
        var password: String? = null,
        var path: String? = null,
        var domain: String? = null,
        var ticket: DestinationTicket? = null,
        var up: Boolean = false,
        var ppm: Int = 0
) : TepidDb by TepidDbDelegate(), TepidExtras by TepidExtrasDelegate()

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class DestinationTicket(
        var up: Boolean = false,
        var reason: String? = null,
        var user: User? = null, //todo change to userjson
        var reported: Date = Date()
)
