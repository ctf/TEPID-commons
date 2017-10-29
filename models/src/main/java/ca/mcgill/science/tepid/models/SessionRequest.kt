package ca.mcgill.science.tepid.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class SessionRequestJson (
        var username: String = "",
        var password: String = "",
        var persistent: Boolean = false,
        var permanent: Boolean = false
)

class SessionRequest(val username: String, val password: String, val persistent: Boolean, val permanent: Boolean) {
    constructor(username: String, password: String) : this(username, password, true, true)
}