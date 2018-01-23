package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.*

/**
 * Created by Allan Wang on 2017-05-03.
 */
data class FullDestination(
        var name: String = "",
        var protocol: String? = null,

        /**
         * Name bound to the destination,
         * used to manage all jobs
         * Can be modified by elders only via Queue Configurations
         */
        var username: String? = null,

        /**
         * Password of the supplied user
         * Can only be modified by elders
         */
        var password: String? = null,

        /**
         * Only modifiable by elders
         */
        var path: String? = null,
        var domain: String? = null,
        var ticket: DestinationTicket? = null,
        var up: Boolean = false,
        var ppm: Int = 0
) : TepidDb by TepidDbDelegate(), TepidExtras by TepidExtrasDelegate() {

    /**
     * Returned a filtered destination variant depending on the session role
     * Some values will be filtered out regardless
     */
    fun toDestination(session: Session): Destination {
        val isElder = session.role == "elder"
        val isCtfer = session.role == "ctfer"
        return Destination(
                name = name,
                protocol = if (isElder) protocol else null,
                username = if (isElder) username else null,
                password = if (isElder) password else null,
                path = if (isElder) path else null,
                domain = if (isElder) domain else null,
                ticket = if (isElder || isCtfer) ticket else null,
                up = up,
                ppm = ppm
        ).withDbData(this)
    }

}

/**
 * As of now, this is an exact copy of [FullDestination],
 * but is filtered based on a supplied session role
 * This adds compile time verification that filtering has been done
 */
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
) : TepidDb by TepidDbDelegate()

data class DestinationTicket(
        var up: Boolean = false,
        var reason: String? = null,
        var user: User? = null,
        var reported: Long = System.currentTimeMillis()
) : TepidJackson
