package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.TEST_USER_SHORT
import ca.mcgill.science.tepid.api.internal.executeExpectingError
import ca.mcgill.science.tepid.test.TestUtils
import ca.mcgill.science.tepid.test.get
import org.junit.Test
import kotlin.test.assertTrue

class QuotaTest {

    @Test
    fun get() {
        TestUtils.testApi.getQuota(TEST_USER_SHORT).get().apply {
            assertTrue(this in 0..99999, "Should the quota really be outside this range?")
        }
    }

    @Test
    fun unauth() {
        TestUtils.testApiUnauth.getQuota(TEST_USER_SHORT).executeExpectingError(401)
    }
}