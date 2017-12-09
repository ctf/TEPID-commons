package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.*
import org.junit.Test

class UserPrintJobsTest {

    @Test
    fun get() {
        api.getUserPrintJobs(TEST_USER).executeTest()
    }

    @Test
    fun unauth() {
        apiUnauth.getUserPrintJobs(TEST_USER).executeExpectingError(401)
    }
}