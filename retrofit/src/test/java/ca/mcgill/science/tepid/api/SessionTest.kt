package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.test.TestUtils
import ca.mcgill.science.tepid.test.get
import org.junit.jupiter.api.Test

class SessionTest {

    @Test
    fun getSession() {
        println(TestUtils.testSession)
    }

    @Test
    fun validateToken() {
        TestUtils.testApiUnauth.validateToken(TestUtils.testUser, TestUtils.testSession?._id ?: "").get()
    }
}