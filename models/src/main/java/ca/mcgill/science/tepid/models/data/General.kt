package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidJackson

/**
 * General response for executing put requests
 */
data class PutResponse(
        var ok: Boolean = false,
        var id: String = ""
) : TepidJackson