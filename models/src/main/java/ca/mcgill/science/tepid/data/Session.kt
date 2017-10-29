package ca.mcgill.science.tepid.data

import ca.mcgill.science.tepid.data.bindings.TepidDb
import ca.mcgill.science.tepid.data.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.data.bindings.USER
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

import java.util.Date

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class SessionJson (
        var role: String = USER,
        var user: User,
        var expiration: Date? = null,
        val persistent: Boolean = true
): TepidDb by TepidDbDelegate() {

    override var type: String? = "session"

    override fun toString(): String {
        return "Session " + this._id
    }

    fun expirationHours(hours: Long) {
        this.expiration = Date(System.currentTimeMillis() + hours * 60 * 60 * 1000)
    }

}

class Session(val role: String, val user: User, val _id: String)