package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.*
import org.junit.Test
import kotlin.test.assertTrue

class QuotaTest {

    @Test
    fun get() {
        api.getQuota(TEST_USER_SHORT).executeTest().apply {
            assertTrue(this in 0..99999, "Should the quota really be outside this range?")
        }
    }

    @Test
    fun unauth() {
        apiUnauth.getQuota(TEST_USER_SHORT).executeExpectingError(401)
    }
}