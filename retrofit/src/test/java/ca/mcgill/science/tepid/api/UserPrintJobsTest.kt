package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.TEST_USER_SHORT
import ca.mcgill.science.tepid.api.internal.executeExpectingError
import ca.mcgill.science.tepid.test.TestUtils
import ca.mcgill.science.tepid.test.get
import org.junit.jupiter.api.Test

class UserPrintJobsTest {

    @Test
    fun get() {
        TestUtils.testApi.getUserPrintJobs(TEST_USER_SHORT).get()
    }

    @Test
    fun unauth() {
        TestUtils.testApiUnauth.getUserPrintJobs(TEST_USER_SHORT).executeExpectingError(401)
    }
}