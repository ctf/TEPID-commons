package ca.mcgill.science.tepid.test

import ca.mcgill.science.tepid.utils.PropUtils
import java.util.*

/**
 * Global attributes to pull from priv.properties for testing
 *
 * This is mainly for internal use
 */
object TestUtils {
    val TEST_AUTH: Pair<String, String> by lazy { TEST_USER to TEST_PASSWORD }

    val TEST_USER: String by lazy {
        PROPS.getProperty("TEST_USER")
    }

    val TEST_PASSWORD: String by lazy {
        PROPS.getProperty("TEST_PASSWORD")
    }

    val TEST_URL: String by lazy {
        var url = PROPS.getProperty("TEST_URL", "")
        url = when (url.toLowerCase()) {
            "tepid" -> TEPID_URL_PRODUCTION
            "testpid", "" -> TEPID_URL_TEST
            else -> url
        }
        println("Using test url $url")
        url
    }

    val IS_NOT_PRODUCTION: Boolean by lazy { TEST_URL != TEPID_URL_PRODUCTION }

    private const val TEPID_URL_PRODUCTION = "https://tepid.science.mcgill.ca:8443/tepid/"
    private const val TEPID_URL_TEST = "http://testpid.science.mcgill.ca:8080/tepid/"
    private const val TEPID_LOCAL = "http://localhost:8080/"

    val PROPS: Properties by lazy {
        PropUtils.loadProps("../priv.properties") ?: throw IllegalArgumentException("No properties found")
    }
}