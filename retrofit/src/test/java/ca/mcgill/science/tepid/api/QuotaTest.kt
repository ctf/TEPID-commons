package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.*
import org.junit.Test

class QuotaTest {

    @Test
    fun get() {
        api.getQuota(TEST_USER).executeTest().apply {
            assert(this in 0..99999) {
                "Should the quota really be outside this range?"
            }
        }
    }

    @Test
    fun unauth() {
        apiUnauth.getQuota(TEST_USER).executeExpectingError(401)
    }
}