package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.models.bindings.USER
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import java.util.*

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Session(
        var role: String = "",
        var user: User,
        var expiration: Date? = null,
        var persistent: Boolean = true
) : TepidDb by TepidDbDelegate() {

    constructor(role: String = "",
                user: User,
                expiration: Long? = null,
                persistent: Boolean = true) : this(role, user, expiration.hoursToDate(), persistent)

    override var type: String? = "session"

    override fun toString() = "Session $_id"

}

private fun Long?.hoursToDate() = if (this == null) null
else Date(System.currentTimeMillis() + this * 60 * 60 * 1000)
