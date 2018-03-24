package ca.mcgill.science.tepid.test

import ca.mcgill.science.tepid.api.ITepid
import ca.mcgill.science.tepid.api.TepidApi
import ca.mcgill.science.tepid.api.executeDirect
import ca.mcgill.science.tepid.models.bindings.TEPID_URL_PRODUCTION
import ca.mcgill.science.tepid.models.bindings.TEPID_URL_TEST
import ca.mcgill.science.tepid.models.data.Session
import ca.mcgill.science.tepid.models.data.SessionRequest
import ca.mcgill.science.tepid.utils.PropUtils
import java.util.*

object TestUtils : TestUtilsDelegate()

/**
 * Global attributes to pull from priv.properties for testing
 */
interface TestUtilsContract {
    val TEST_AUTH: Pair<String, String>
    val TEST_USER: String
    val TEST_PASSWORD: String
    val TEST_TOKEN: String
    val TEST_URL: String
    fun getUrl(key: String): String

    val IS_NOT_PRODUCTION: Boolean
    val HAS_TEST_USER: Boolean
    val PROPS: Properties
    val TEST_SESSION: Session?
}

open class TestUtilsDelegate(vararg propPath: String = arrayOf("priv.properties", "../priv.properties")) : TestUtilsContract {

    private operator fun get(key: String) = PROPS.getProperty(key, "")

    override val TEST_AUTH: Pair<String, String> by lazy { TEST_USER to TEST_PASSWORD }

    override val TEST_USER: String by lazy {
        this["TEST_USER"]
    }

    override val TEST_PASSWORD: String by lazy {
        this["TEST_PASSWORD"]
    }

    override val TEST_TOKEN: String by lazy {
        this["TEST_TOKEN"]
    }

    override val TEST_URL: String by lazy {
        val url = getUrl("TEST_URL")
        println("Using test url $url")
        url
    }

    override fun getUrl(key: String): String {
        var url = PROPS.getProperty(key, TEPID_URL_TEST)
        url = when (url.toLowerCase()) {
            "tepid" -> TEPID_URL_PRODUCTION
            "testpid", "" -> TEPID_URL_TEST
            else -> url
        }
        return url
    }

    override val IS_NOT_PRODUCTION: Boolean by lazy { TEST_URL != TEPID_URL_PRODUCTION }

    override val HAS_TEST_USER: Boolean by lazy {
        TEST_USER.isNotBlank() && (TEST_PASSWORD.isNotBlank() || TEST_TOKEN.isNotBlank())
    }

    override val PROPS: Properties by lazy {
        PropUtils.loadProps(*propPath) ?: defaultProps()
    }

    private fun defaultProps(): Properties {
        println("Could not find test props")
        return Properties()
    }

    override val TEST_SESSION: Session? by lazy {
        val api: ITepid by lazy { TepidApi(TEST_URL, true).create() }
        if (TEST_PASSWORD.isNotBlank()) {
            val session = api.getSession(
                    SessionRequest(TEST_USER, TEST_PASSWORD, true, true)
            ).executeDirect()
            if (session != null)
                return@lazy session
        }
        if (TEST_TOKEN.isNotBlank()) {
            val session = api.validateToken(TEST_USER, TEST_TOKEN).executeDirect()
            if (session != null)
                return@lazy session
        }
        null
    }

}