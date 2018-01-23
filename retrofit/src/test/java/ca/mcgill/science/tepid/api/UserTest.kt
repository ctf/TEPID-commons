package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.*
import org.junit.Test
import kotlin.test.assertTrue

class UserTest {

    @Test
    fun isConfigured() {
        val result = apiUnauth.isConfigured().executeTest()
        assertTrue(result, "Tepid is not configured")
    }

    @Test
    fun getByShortUser() {
        api.getUser(TEST_USER_SHORT).executeTest().assertTestUser()
    }

    @Test
    fun unauthByShortUser() {
        apiUnauth.getUser(TEST_USER_SHORT).executeExpectingError(401)
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