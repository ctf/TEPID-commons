package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.TEST_USER_SHORT
import ca.mcgill.science.tepid.api.internal.assertTestUser
import ca.mcgill.science.tepid.api.internal.executeExpectingError
import ca.mcgill.science.tepid.test.TestUtils
import ca.mcgill.science.tepid.test.get
import org.junit.Test
import kotlin.test.assertTrue

class UserTest {

    @Test
    fun isConfigured() {
        val result = TestUtils.testApiUnauth.isConfigured().get()
        assertTrue(result, "Tepid is not configured")
    }

    @Test
    fun getByShortUser() {
        TestUtils.testApi.getUser(TEST_USER_SHORT).get().assertTestUser()
    }

    @Test
    fun unauthByShortUser() {
        TestUtils.testApiUnauth.getUser(TEST_USER_SHORT).executeExpectingError(401)
    }

    @Test
    fun getById() {
        TestUtils.testApi.getUser(TEST_USER_ID).get().assertTestUser()
    }

    @Test
    fun unauthById() {
        TestUtils.testApiUnauth.getUser(TEST_USER_ID).executeExpectingError(401)
    }

}