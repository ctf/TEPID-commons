package ca.mcgill.science.tepid.core

/**
 * Created by Allan Wang on 2017-10-29.
 */
enum class PrinterId(val serialNumber: String) {
    _1B16_North(***REMOVED***),
    _1B16_South(***REMOVED***),
    _1B17_North(***REMOVED***),
    _1B17_South(***REMOVED***),
    _1B18_North(***REMOVED***);

    //name matches the true printer name on TEPID
    fun getName(): String {
        return toString().substring(1).replace("_", "-")
    }
}