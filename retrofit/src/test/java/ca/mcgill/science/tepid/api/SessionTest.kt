package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.apiUnauth
import ca.mcgill.science.tepid.api.internal.executeTest
import ca.mcgill.science.tepid.api.internal.session
import ca.mcgill.science.tepid.test.TestUtils
import org.junit.Test

class SessionTest {

    @Test
    fun getSession() {
        println(session)
    }

    @Test
    fun validateToken() {
        apiUnauth.validateToken(TestUtils.TEST_USER, session.getId()).executeTest()
    }
}