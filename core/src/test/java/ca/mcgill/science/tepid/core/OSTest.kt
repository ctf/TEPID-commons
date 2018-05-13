package ca.mcgill.science.tepid.core

import org.junit.Test

class OSTest {
    @Test
    fun os() {
        println("Current os: ${System.getProperty("os.name")} -> $os")
    }
}