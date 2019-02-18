package ca.mcgill.science.tepid.test

import ca.allanwang.kit.logger.Loggable
import ca.allanwang.kit.logger.WithLogging
import ca.allanwang.kit.props.PropHolder
import ca.mcgill.science.tepid.api.*
import ca.mcgill.science.tepid.models.data.Session
import ca.mcgill.science.tepid.models.data.SessionRequest
import ca.mcgill.science.tepid.utils.PropsLDAP
import ca.mcgill.science.tepid.utils.PropsLDAPTestUser
import ca.mcgill.science.tepid.utils.PropsURL

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

    val isNotProduction: Boolean
    val hasTestUser: Boolean
    val testSession: Session?

    val testApiUnauth: ITepid
    val testApi: ITepid
    val testScreensaverApi: ITepidScreensaver
}

open class TestUtilsDelegate(
        vararg propPath: String = arrayOf("priv.properties", "../priv.properties")
) : PropHolder(*propPath), Loggable by WithLogging(), TestUtilsContract {

    override val testAuth: Pair<String, String> by lazy { testUser to testPassword }

    override val testUser: String = PropsLDAPTestUser.TEST_USER

    override val testPassword: String = PropsLDAPTestUser.TEST_PASSWORD

    override val testToken: String by string("TEST_TOKEN")

    override val testUrl: String = PropsURL.SERVER_URL_TESTING!!

    override val isNotProduction: Boolean by lazy { PropsURL.TESTING?.toBoolean() ?: true }

    override val hasTestUser: Boolean by lazy {
        testUser.isNotBlank() && (testPassword.isNotBlank() || testToken.isNotBlank())
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

    override val testScreensaverApi: ITepidScreensaver by lazy {
        when {
            testUrl.isBlank() -> log.error("Requesting testScreensaverApi for empty url")
            else -> log.info("Initialized testScreensaverApi")
        }
        TepidScreensaverApi(testUrl, true).create()
    }

}