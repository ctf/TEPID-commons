package ca.mcgill.science.tepid.api.internal

import ca.mcgill.science.tepid.api.ITepid
import ca.mcgill.science.tepid.api.TepidApi
import ca.mcgill.science.tepid.models.bindings.TEPID_URL_TEST
import java.io.File
import java.io.FileInputStream
import java.util.*

const val url = TEPID_URL_TEST

val apiUnauth: ITepid by lazy { TepidApi(url, true).create() }

val api: ITepid by lazy { TepidApi(url, true).create { token = getToken() } }

private fun getToken(): String {
    val file = File("../priv.properties")
    if (!file.isFile) {
        println("No token found at ${file.absolutePath}")
        return ""
    }
    val props = Properties()
    FileInputStream(file).use { props.load(it) }
    return props.getProperty("TOKEN", "")
}