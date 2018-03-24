package ca.mcgill.science.tepid.test

import ca.mcgill.science.tepid.api.ITepid
import ca.mcgill.science.tepid.api.TepidApi
import ca.mcgill.science.tepid.api.executeDirect
import ca.mcgill.science.tepid.models.bindings.TEPID_URL_PRODUCTION
import ca.mcgill.science.tepid.models.bindings.TEPID_URL_TEST
import ca.mcgill.science.tepid.models.data.Session
import ca.mcgill.science.tepid.models.data.SessionRequest
import ca.mcgill.science.tepid.utils.Loggable
import ca.mcgill.science.tepid.utils.PropUtils
import ca.mcgill.science.tepid.utils.WithLogging
import java.util.*

object TestUtils : TestUtilsDelegate()

/**
 * Global attributes to pull from priv.properties for testing
 */
interface TestUtilsContract {
    val testAuth: Pair<String, String>
    val testUser: String
    val testPassword: String
    val testToken: String
    val testUrl: String
    fun getUrl(key: String): String

    val isNotProduction: Boolean
    val hasTestUser: Boolean
    val props: Properties
    val testSession: Session?

    val testApiUnauth: ITepid
    val testApi: ITepid
}

open class TestUtilsDelegate(
        vararg propPath: String = arrayOf("priv.properties", "../priv.properties")
) : Loggable by WithLogging(), TestUtilsContract {

    operator fun get(key: String): String = props.getProperty(key, "")

    override val testAuth: Pair<String, String> by lazy { testUser to testPassword }

    override val testUser: String by lazy {
        this["TEST_USER"]
    }

    override val testPassword: String by lazy {
        this["TEST_PASSWORD"]
    }

    override val testToken: String by lazy {
        this["TEST_TOKEN"]
    }

    override val testUrl: String by lazy {
        val url = getUrl("TEST_URL")
        println("Using test url $url")
        url
    }

    override fun getUrl(key: String): String {
        var url = props.getProperty(key, TEPID_URL_TEST)
        url = when (url.toLowerCase()) {
            "tepid" -> TEPID_URL_PRODUCTION
            "testpid", "" -> TEPID_URL_TEST
            else -> url
        }
        return url
    }

    override val isNotProduction: Boolean by lazy { testUrl != TEPID_URL_PRODUCTION }

    override val hasTestUser: Boolean by lazy {
        testUser.isNotBlank() && (testPassword.isNotBlank() || testToken.isNotBlank())
    }

    override val props: Properties by lazy {
        PropUtils.loadProps(*propPath) ?: defaultProps()
    }

    private fun defaultProps(): Properties {
        println("Could not find test props")
        return Properties()
    }

    override val testSession: Session? by lazy {
        val api: ITepid by lazy { TepidApi(testUrl, true).create() }
        if (testPassword.isNotBlank()) {
            val session = api.getSession(
                    SessionRequest(testUser, testPassword, true, true)
            ).executeDirect()
            if (session != null)
                return@lazy session
        }
        if (testToken.isNotBlank()) {
            val session = api.validateToken(testUser, testToken).executeDirect()
            if (session != null)
                return@lazy session
        }
        null
    }

    override val testApiUnauth: ITepid by lazy {
        when {
            testUrl.isBlank() -> log.error("Requesting apiUnauth for empty url")
            else -> log.info("Initialized test apiUnauth")
        }
        TepidApi(testUrl, true).create()
    }

    override val testApi: ITepid by lazy {
        when {
            testUrl.isBlank() -> log.error("Requesting api for empty url")
            !hasTestUser -> {
                log.error("--------------------------------------------------------------------------\n")
                log.error("Requesting api for $testUrl with a blank username or password")
                log.error("\n--------------------------------------------------------------------------")
            }
        }
        val session = testSession
        if (session == null) {
            println("Could not retrieve authenticated test api")
            return@lazy testApiUnauth
        }
        TepidApi(testUrl, true).create {
            tokenRetriever = session::authHeader
        }
    }

}