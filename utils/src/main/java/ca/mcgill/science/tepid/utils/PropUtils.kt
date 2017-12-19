package ca.mcgill.science.tepid.utils

import java.io.File
import java.io.FileInputStream
import java.util.*

object PropUtils {

    /**
     * Attempts to load properties from the provided paths
     * If any file exist, a set of [Properties] will be returned,
     * else null
     */
    fun loadProps(vararg path: String): Properties? {
        val valid = path.map(::File).firstOrNull(File::exists) ?: return null
        println("Found properties at ${valid.absolutePath}")
        val props = Properties()
        FileInputStream(valid).use { props.load(it) }
        return props
    }

}