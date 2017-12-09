package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.executeTest
import ca.mcgill.science.tepid.models.bindings.TEPID_URL_TEST
import org.junit.BeforeClass
import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.test.assertNotNull

/**
 * Created by Allan Wang on 2017-10-28.
 */
class ApiTest {

    companion object {
        lateinit var api: ITepid

        @BeforeClass
        @JvmStatic
        fun init() {
            api = TepidApi(TEPID_URL_TEST, true).create { token = getToken() }
        }

        private fun getToken(): String {
            val file = File("../priv.properties")
            if (!file.isFile) {
                println("No token found at ${file.absolutePath}")
                return ""
            }
            val props = Properties()
            FileInputStream(file).use { props.load(it) }
            println("Found token")
            return props.getProperty("TOKEN", "")
        }
    }

    @Test
    fun getAbout() {
        api.getAbout().executeTest().apply {
            assertNotNull(hash)
        }
    }

    @Test
    fun getPrinterInfo() {
        api.getPrinterInfo().executeTest().apply { }
    }

}