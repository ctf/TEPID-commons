package ca.mcgill.science.tepid.test

import ca.mcgill.science.tepid.api.*
import ca.mcgill.science.tepid.models.data.Session
import ca.mcgill.science.tepid.models.data.SessionRequest
import ca.mcgill.science.tepid.utils.Loggable
import ca.mcgill.science.tepid.utils.PropsLDAPTestUser
import ca.mcgill.science.tepid.utils.PropsURL
import ca.mcgill.science.tepid.utils.WithLogging

object TestUtils : TestUtilsDelegate(
        PropsLDAPTestUser.TEST_USER,
        PropsLDAPTestUser.TEST_PASSWORD,
        PropsURL.SERVER_URL_PRODUCTION!!,
        PropsURL.TESTING!!.toBoolean()
)

/**
 * Global attributes to pull from properties for testing
 */
interface TestUtilsContract {
    abstract val GUID_REGEX: Regex
    val testAuth: Pair<String, String>
    val testUser: String
    val testPassword: String
    val testUrl: String

    val isNotProduction: Boolean
    val hasTestUser: Boolean
    val testSession: Session?

    val testApiUnauth: ITepid
    val testApi: ITepid
    val testScreensaverApi: ITepidScreensaver
}

open class TestUtilsDelegate(
        override val testUser: String,
        override val testPassword: String,
        override val testUrl: String,
        override val isNotProduction: Boolean = true
) : Loggable by WithLogging(), TestUtilsContract {

    override val testAuth: Pair<String, String> by lazy { testUser to testPassword }

    override val hasTestUser: Boolean by lazy {
        testUser.isNotBlank() && (testPassword.isNotBlank())
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

    override val testScreensaverApi: ITepidScreensaver by lazy {
        when {
            testUrl.isBlank() -> log.error("Requesting testScreensaverApi for empty url")
            else -> log.info("Initialized testScreensaverApi")
        }
        TepidScreensaverApi(testUrl, true).create()
    }

    override val GUID_REGEX = """(\{){0,1}[0-9a-fA-F]{8}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{12}(\}){0,1}""".toRegex()


}