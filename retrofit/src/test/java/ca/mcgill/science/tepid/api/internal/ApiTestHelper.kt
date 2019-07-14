package ca.mcgill.science.tepid.api.internal

import ca.mcgill.science.tepid.models.data.User
import ca.mcgill.science.tepid.utils.PropsLDAPTestUser
import retrofit2.Call
import kotlin.test.assertEquals
import kotlin.test.fail

/**
 * Execute a response while expecting an error of code [expectedCode]
 */
internal fun <T> Call<T>.executeExpectingError(expectedCode: Int, message: String? = null) {
    try {
        val response = execute()
        if (response.isSuccessful)
            fail("Successful response received when expecting failure: ${response.body()}")
        if (response.code() != expectedCode)
            fail("Unsuccessful response with ${response.code()} code rather than $expectedCode")
        println(response.errorBody()?.string())
    } catch (exception: Exception) {
        fail("An exception occurred: ${exception.message}")
    }
}

val TEST_USER_SHORT = PropsLDAPTestUser.TEST_USER
val TEST_USER_ID = PropsLDAPTestUser.TEST_ID

internal fun User.assertTestUser(){
    assertEquals(TEST_USER_SHORT, shortUser)
    assertEquals(TEST_USER_ID, studentId.toString())
}