package ca.mcgill.science.tepid.models.bindings

import java.util.*

/**
 * Created by Allan Wang on 2017-10-29.
 */
const val CTFER = "ctfer"
const val ELDER = "elder"
const val USER = "user"
const val ADMIN = "admin"
const val LOCAL = "local"

const val TEPID_URL_PRODUCTION = "https://tepid.science.mcgill.ca:8443/tepid/"
const val TEPID_URL_TEST = "https://testpid.science.mcgill.ca:8443/tepid/"
const val TEPID_LOCAL = "http://localhost:8080/"

internal val elderGroups = arrayOf("***REMOVED***")
internal val userGroups: Array<String>
    get() {
        val cal = Calendar.getInstance()
        return arrayOf("***REMOVED***", "***REMOVED***" + cal.get(Calendar.YEAR) + if (cal.get(Calendar.MONTH) < 8) "W" else "F")
    }
internal val ctferGroups = arrayOf("***REMOVED***", "***REMOVED***")