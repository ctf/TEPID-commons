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

/**
 * Response used whenever a request fails on the server end
 * Note that to keep things consistent, everything that isn't [status] and [message]
 * should have defaults.
 *
 * Extra parameters will only be updated if an error response is explicitly created
 */
data class ErrorResponse(
        val status: Int,
        val message: String,
        val extras: List<String> = emptyList()
) : TepidJackson

data class ChangeDelta(
        val id: String,
        val rev: String
) : TepidJackson