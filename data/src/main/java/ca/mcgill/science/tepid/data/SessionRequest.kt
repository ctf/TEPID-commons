package ca.mcgill.science.tepid.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class SessionRequest @JvmOverloads constructor(
        var username: String = "",
        var password: String = "",
        var persistent: Boolean = false,
        var permanent: Boolean = false
)