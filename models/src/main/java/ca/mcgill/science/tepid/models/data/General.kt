package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidJackson

/**
 * General response for executing put requests, as denoted by CouchDb's documentation
 */
data class PutResponse(
        var ok: Boolean = false,
        var id: String = "",
        var rev: String = ""
) : TepidJackson

data class ErrorResponse(
        var message: String = ""
) : TepidJackson {

    override fun toString(): String {
        return "{ message: \"$message\" }"
    }
}