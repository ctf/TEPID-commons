package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.assertComponentsEqual
import ca.mcgill.science.tepid.api.internal.executeTest
import ca.mcgill.science.tepid.models.bindings.TEPID_URL_TEST
import ca.mcgill.science.tepid.models.data.User
import ca.mcgill.science.tepid.models.enums.PrinterId
import org.junit.BeforeClass
import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Created by Allan Wang on 2017-10-28.
 *
 * Though a lot of the tests aren't validated content wise, the responses themselves are parsed and confirmed using Moshi
 *
 * For the most part, we will test get methods and error methods to avoid cluttering the db
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
        val data = api.getPrinterInfo().executeTest()
        assert(data.size == PrinterId.values.size) {
            "Not all printers are mapped"
        }
        val names = data.map { it.value.name }.toSet()
        PrinterId.values.forEach {
            assert(names.contains(it.toString())) {
                "PrinterId.toString() should reflect actual name; did not find $it"
            }
        }
        data.map { it.value }.forEach {
            assert((it.ticket == null) == it.up) {
                "Printers that are up should not have tickets, and printers that are down should have tickets"
            }
            assert(it.domain == "***REMOVED***")
        }
    }

    private fun User.testIsAllan() = assertComponentsEqual {
        listOf(
                givenName to "Allan",
                lastName to "Wang",
                longUser to "***REMOVED***",
                email to "***REMOVED***",
                faculty to "***REMOVED***",
                realName to "Allan Wang",
                studentId to ***REMOVED***
        )
    }.apply {
        assertTrue(groups.isNotEmpty())
    }

    @Test
    fun getUserByShortUser() {
        api.getUser("***REMOVED***").executeTest().testIsAllan()
    }

    @Test
    fun getUserById() {
        api.getUser(***REMOVED***).executeTest().testIsAllan()
    }

    @Test
    fun getQuota() {
        api.getQuota("***REMOVED***").executeTest().apply {
            assert(this in 0..99999) {
                "Should the quota really be outside this range?"
            }
        }
    }

    @Test
    fun getPrintQueue() {
        PrinterId.values.map(PrinterId::toString).forEach {
            api.getPrintQueue(it, 10).executeTest()
        }
    }

    @Test
    fun getUserPrintJobs() {
        api.getUserPrintJobs("***REMOVED***").executeTest()
    }

    @Test
    fun getUserQuery() {
        api.getUserQuery("awang", 5).executeTest().apply {
            assert(isNotEmpty())
        }
    }
}