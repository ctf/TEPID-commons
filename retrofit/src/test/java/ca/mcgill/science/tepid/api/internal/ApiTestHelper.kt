package ca.mcgill.science.tepid.api.internal

import ca.mcgill.science.tepid.models.data.User
import retrofit2.Call
import retrofit2.Response
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Given two variables with the same type, map their respective values and compare them individually
 */
internal fun <T> T.assertComponentsEqual(expected: T, values: (T) -> List<Any?>): T {
    val expectedValues = values(expected)
    val actualValues = values(this)
    var valid = true
    expectedValues.zip(actualValues).forEachIndexed { i, (exp, act) ->
        if (act != exp) {
            valid = false
            System.err.println("Component $i mismatch: expected $exp, actually $act")
        }
    }
    if (!valid) fail("Not all components matched")
    return this
}

/**
 * Given one variable, make its components to expected values
 */
internal fun <T> T.assertComponentsEqual(value: T.() -> List<Pair<Any?, Any?>>): T {
    var valid = true
    this.value().forEachIndexed { index, (act, exp) ->
        if (act != exp) {
            valid = false
            System.err.println("Component $index mismatch: expected $exp, actually $act")
        }
    }
    if (!valid) fail("Not all components matched")
    return this
}

/**
 * Generic version of [executeDiy]
 */
internal fun <T> Call<T>.executeTest(): T {
    try {
        val response = execute()
        if (response.isSuccessful) {
            val body = response.body() ?: fail("Successful test response has a null body")
            println("Successful test response received:\n$body")
            return body
        }
        fail("Unsuccessful ${response.code()} test response")
    } catch (exception: Exception) {
        fail("An exception occurred: ${exception.message}")
    }
}

/**
 * Execute a response while expecting an error of code [expectedCode]
 * An optional [action] is available if the code matches the expected code
 */
internal fun <T> Call<T>.executeExpectingError(expectedCode: Int, action: Response<T>.() -> Unit = {}) {
    try {
        val response = execute()
        if (response.isSuccessful)
            fail("Successful response received when expecting failure: ${response.body()}")
        if (response.code() != expectedCode)
            fail("Unsuccessful response with ${response.code()} code rather than $expectedCode")
        response.action()
    } catch (exception: Exception) {
        fail("An exception occurred: ${exception.message}")
    }
}

const val TEST_USER = "***REMOVED***"
const val TEST_USER_ID = ***REMOVED***

internal fun User.assertTestUser() = assertComponentsEqual {
    listOf(
            givenName to "Allan",
            lastName to "Wang",
            longUser to "***REMOVED***",
            email to "***REMOVED***",
            faculty to "***REMOVED***",
            realName to "Allan Wang",
            shortUser to TEST_USER,
            studentId to TEST_USER_ID
    )
}.apply {
    assertTrue(groups.isNotEmpty())
}