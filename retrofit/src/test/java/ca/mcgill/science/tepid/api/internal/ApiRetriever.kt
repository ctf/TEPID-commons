package ca.mcgill.science.tepid.api.internal

import ca.mcgill.science.tepid.api.ITepid
import ca.mcgill.science.tepid.api.TepidApi
import ca.mcgill.science.tepid.models.data.SessionRequest
import ca.mcgill.science.tepid.test.TEST_PASSWORD
import ca.mcgill.science.tepid.test.TEST_URL

val apiUnauth: ITepid by lazy { TepidApi(TEST_URL, true).create() }

val api: ITepid by lazy {
    val session = apiUnauth.getSession(
            SessionRequest(TEST_USER, TEST_PASSWORD, false, false)
    ).executeTest()
    TepidApi(TEST_URL, true).create {
        tokenRetriever = { session.token }
    }
}
