package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidJackson

data class SessionRequest(
        var username: String = "",
        var password: String = "",
        var persistent: Boolean = true,
        var permanent: Boolean = true
) : TepidJackson