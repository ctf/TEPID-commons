package ca.mcgill.science.tepid.api.internal

import ca.mcgill.science.tepid.models.data.User
import ca.mcgill.science.tepid.test.assertComponentsEqual
import retrofit2.Call
import java.util.concurrent.CompletableFuture
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

const val TEST_USER_SHORT = "***REMOVED***"
const val TEST_USER_ID = ***REMOVED***

internal fun User.assertTestUser() = assertComponentsEqual {
    listOf(
            givenName to "Allan",
            lastName to "Wang",
            longUser to "***REMOVED***",
            email to "***REMOVED***",
            faculty to "***REMOVED***",
            realName to "Allan Wang",
            shortUser to TEST_USER_SHORT,
            studentId to TEST_USER_ID
    )
}