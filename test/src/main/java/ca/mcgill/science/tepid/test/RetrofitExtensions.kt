package ca.mcgill.science.tepid.test

import retrofit2.Call
import java.util.concurrent.CompletableFuture
import kotlin.test.fail

/**
 * Equivalent to [Call.execute] with validation checks and a guaranteed data return if successful
 */
fun <T> Call<T>.get(): T {
    try {
        val response = execute()
        if (response.isSuccessful) {
            val body = response.body() ?: fail("Successful test response has a null body")
            println("Successful test response received:\n$body")
            return body
        }
        fail("Unsuccessful ${response.code()} test response:\n${response.errorBody()?.string()}")
    } catch (exception: Exception) {
        fail("An exception occurred: ${exception.message}")
    }
}

fun <T> Call<T>.getFuture() = CompletableFuture.supplyAsync { get() }
