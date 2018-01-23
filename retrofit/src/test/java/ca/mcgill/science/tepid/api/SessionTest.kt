package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.apiUnauth
import ca.mcgill.science.tepid.api.internal.executeTest
import ca.mcgill.science.tepid.models.data.SessionRequest
import ca.mcgill.science.tepid.test.TestUtils
import org.junit.Test

class SessionTest {

    @Test
    fun getSession() {
        val session = apiUnauth.getSession(
                SessionRequest(TestUtils.TEST_USER, TestUtils.TEST_PASSWORD, false, false)
        ).executeTest()
        println(session.token)
    }
}