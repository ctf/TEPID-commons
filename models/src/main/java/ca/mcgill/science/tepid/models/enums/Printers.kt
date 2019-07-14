package ca.mcgill.science.tepid.models.enums

class PrinterId(val name:String, val serialNumber: String) {

    //name matches the true printer name on TEPID
    override fun toString() = name.substring(1)
            .replace("_", "-")
}

class Room(val name:String, val printers: List<PrinterId>) {}
