package ca.mcgill.science.tepid.models.data

/**
 * Response when toggling colour printing
 */
data class ColorResponse(
        var ok: Boolean = false,
        var id: String = ""
)