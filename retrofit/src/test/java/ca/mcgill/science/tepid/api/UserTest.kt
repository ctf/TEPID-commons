package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.*
import org.junit.Test

class UserTest {

    @Test
    fun getByShortUser() {
        api.getUser(TEST_USER).executeTest().assertTestUser()
    }

    @Test
    fun unauthByShortUser() {
        apiUnauth.getUser(TEST_USER).executeExpectingError(401)
    }

    @Test
    fun getById() {
        api.getUser(TEST_USER_ID).executeTest().assertTestUser()
    }

    @Test
    fun unauthById() {
        apiUnauth.getUser(TEST_USER_ID).executeExpectingError(401)
    }

}