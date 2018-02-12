package ca.mcgill.science.tepid.api.internal

import ca.mcgill.science.tepid.api.ITepid
import ca.mcgill.science.tepid.api.TepidApi
import ca.mcgill.science.tepid.models.data.Session
import ca.mcgill.science.tepid.models.data.FullSession
import ca.mcgill.science.tepid.models.data.SessionRequest
import ca.mcgill.science.tepid.test.TestUtils
import org.apache.logging.log4j.LogManager

private val log = LogManager.getLogger("ApiRetriever")

val apiUnauth: ITepid by lazy {
    when {
        TestUtils.TEST_URL.isBlank() -> log.error("Requesting apiUnauth for empty url")
        else -> log.info("Initialized test apiUnauth")
    }
    TepidApi(TestUtils.TEST_URL, true).create()
}

val api: ITepid by lazy {
    when {
        TestUtils.TEST_URL.isBlank() -> log.error("Requesting api for empty url")
        TestUtils.TEST_USER.isBlank() || TestUtils.TEST_PASSWORD.isBlank() ->
            log.error("Requesting api for ${TestUtils.TEST_URL} with a blank username or password")
    }
    TepidApi(TestUtils.TEST_URL, true).create {
        tokenRetriever = {
            println(session.authHeader)
            println(FullSession.decodeHeader(session.authHeader))
            session.authHeader
        }
    }
}

val session: Session by lazy {
    val session = apiUnauth.getSession(
            SessionRequest(TestUtils.TEST_USER, TestUtils.TEST_PASSWORD, false, false)).executeTest()
    log.info("Initialized test api $session")
    log.info("AAA ${session._id}")
    session
}