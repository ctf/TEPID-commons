package ca.mcgill.science.tepid.models.enums

/**
 * Collection of potential print errors
 */
enum class PrintError(val display: String) {
    GENERIC("Generic Failure"),
    IMMEDIATE("Print Failure"),
    INSUFFICIENT_QUOTA("Insufficient Quota"),
    INVALID_DESTINATION("Invalid Destination"),
    COLOR_DISABLED("Color disabled"),
    TOO_MANY_PAGES("Too Many Pages"),
    NO_INTERNET("No Internet Detected"),
    ;
}