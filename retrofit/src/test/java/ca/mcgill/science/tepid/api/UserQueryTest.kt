package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.executeExpectingError
import ca.mcgill.science.tepid.test.TestUtils
import ca.mcgill.science.tepid.test.get
import org.junit.Test

class UserQueryTest {

    @Test
    fun get() {
        TestUtils.testApi.queryUsers("awang", 5).get().apply {
            assert(isNotEmpty())
        }
    }

    @Test
    fun unauth() {
        TestUtils.testApiUnauth.queryUsers("awang", 5).executeExpectingError(401)
    }

}