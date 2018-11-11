package ca.mcgill.science.tepid.utils

import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * A PropLoader represents a single source of properties
 * It does not have conception of what those properties might be
 * We've figured that maybe being able to load properties from the web would be useful,
 *  so I've left the Properties field to be implemented by the subclass
 * Based on the props part of KIT https://github.com/allanwang/KIT developed by Allan Wang. Thanks Allan!
 */

interface PropLoader {

    /**
     * @return Success loading props
     */
    fun loadProps(): Boolean

    fun get(key: String): String?

}

class FilePropLoader(val filePath: String) : PropLoader, WithLogging() {
    val props = Properties()

    init {
        this.loadProps()
    }


    override fun loadProps(): Boolean {
        val file = File(filePath)
        if (file.isFile) {
            FileInputStream(file).use(props::load)
            log.info("Found readable file at $filePath")
            return true
        } else {
            log.info("Did not find readable file at $filePath")
            return false
        }
    }

    override fun get(key: String): String? {
        return props.getProperty(key)
    }
}

class JarPropLoader(val fileName: String) : PropLoader, WithLogging() {
    val props = Properties()

    init {
        this.loadProps()
    }

    override fun loadProps(): Boolean {
        val file = this.javaClass.getResourceAsStream(fileName)
        if (file != null) {
            props.load(file)
            log.info("Found readable file inside JAR at $fileName")
            return true
        } else {
            log.info("Did not find readable file inside JAR at $fileName")
            return false
        }
    }

    override fun get(key: String): String? {
        return props.getProperty(key)
    }

}