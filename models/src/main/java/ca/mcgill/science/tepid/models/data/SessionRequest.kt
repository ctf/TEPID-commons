package ca.mcgill.science.tepid.models.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class SessionRequest(
        var username: String = "",
        @Transient var password: String = "",
        var persistent: Boolean = false,
        var permanent: Boolean = false
) {
    constructor(username: String, password: String) : this(username, password, true, true)
}