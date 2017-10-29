package ca.mcgill.science.tepid.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

import java.util.Date

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Session @JvmOverloads constructor(
        var _id: String = "",
        var _rev: String = "",
        var role: String? = null,
        val type: String = "session",
        var user: User? = null,
        var expiration: Date? = null,
        val persistent: Boolean = true
) {
    override fun toString(): String {
        return "Session " + this._id
    }

    fun expirationHours(hours: Long) {
        this.expiration = Date(System.currentTimeMillis() + hours * 60 * 60 * 1000)
    }

}
