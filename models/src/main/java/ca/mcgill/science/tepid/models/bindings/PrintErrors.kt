package ca.mcgill.science.tepid.models.bindings

/**
 * Collection of potential print errors
 * Items should be in lower case, as they will be compared against other messages converted to lower case
 */
object PrintErrors {
    const val INSUFFICIENT_QUOTA = "insufficient quota"
    const val COLOR_DISABLED = "color disabled"
    const val INVALID_DESTINATION = "invalid destination"
}