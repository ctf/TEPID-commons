package ca.mcgill.science.tepid.models.enums

/**
 * Created by Allan Wang on 2017-12-09,.
 */
enum class PrinterId(val serialNumber: String) {
    _1B16_North(***REMOVED***),
    _1B16_South(***REMOVED***),
    _1B17_North(***REMOVED***),
    _1B17_South(***REMOVED***),
    _1B18_North(***REMOVED***);

    //name matches the true printer name on TEPID
    override fun toString() = name.substring(1)
            .replace("_", "-")

    companion object {
        val values = values()
    }
}

enum class Room(vararg val printers: PrinterId) {
    _1B16(PrinterId._1B16_North, PrinterId._1B16_South),
    _1B17(PrinterId._1B17_North, PrinterId._1B17_South),
    _1B18(PrinterId._1B18_North);

    //name matches the true room name on TEPID
    override fun toString() = name.substring(1)

    companion object {
        val values = values()
    }
}