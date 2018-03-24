package ca.mcgill.science.tepid.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T : Any> Call<T>.fetch(consumer: (data: T?, err: Throwable?) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            consumer(response.body(), null)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            consumer(null, t)
        }
    })
}