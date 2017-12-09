package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.api
import ca.mcgill.science.tepid.api.internal.apiUnauth
import ca.mcgill.science.tepid.api.internal.executeExpectingError
import ca.mcgill.science.tepid.api.internal.executeTest
import org.junit.Test

class UserQueryTest {

    @Test
    fun get() {
        api.getUserQuery("awang", 5).executeTest().apply {
            assert(isNotEmpty())
        }
    }

    @Test
    fun unauth() {
        apiUnauth.getUserQuery("awang", 5).executeExpectingError(401)
    }

}