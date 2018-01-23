package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidJackson

/**
 * Response when toggling colour printing
 */
data class ColorResponse(
        var ok: Boolean = false,
        var id: String = ""
) : TepidJackson