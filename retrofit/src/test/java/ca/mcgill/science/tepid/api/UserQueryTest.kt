package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.api
import ca.mcgill.science.tepid.api.internal.apiUnauth
import ca.mcgill.science.tepid.api.internal.executeExpectingError
import ca.mcgill.science.tepid.api.internal.get
import org.junit.Test

class UserQueryTest {

    @Test
    fun get() {
        api.queryUsers("awang", 5).get().apply {
            assert(isNotEmpty())
        }
    }

    @Test
    fun unauth() {
        apiUnauth.queryUsers("awang", 5).executeExpectingError(401)
    }

}