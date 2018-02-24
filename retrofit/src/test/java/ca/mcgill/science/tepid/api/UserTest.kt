package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.*
import org.junit.Test
import kotlin.test.assertTrue

class UserTest {

    @Test
    fun isConfigured() {
        val result = apiUnauth.isConfigured().get()
        assertTrue(result, "Tepid is not configured")
    }

    @Test
    fun getByShortUser() {
        api.getUser(TEST_USER_SHORT).get().assertTestUser()
    }

    @Test
    fun unauthByShortUser() {
        apiUnauth.getUser(TEST_USER_SHORT).executeExpectingError(401)
    }

    @Test
    fun getById() {
        api.getUser(TEST_USER_ID).get().assertTestUser()
    }

    @Test
    fun unauthById() {
        apiUnauth.getUser(TEST_USER_ID).executeExpectingError(401)
    }

}