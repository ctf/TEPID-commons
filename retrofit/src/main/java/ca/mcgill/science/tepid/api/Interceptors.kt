package ca.mcgill.science.tepid.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Allan Wang on 2017-10-29.
 */

abstract class BaseInterceptor : Interceptor {

    abstract fun apply(request: Request.Builder, originalChain: Interceptor.Chain)

    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request().newBuilder()
        apply(request, chain)
        return chain.proceed(request.build())
    }

}

/**
 * Injects the token to each request
 */
class TokenInterceptor(val token: String, val injector: (Request) -> Boolean = { true }) : BaseInterceptor() {

    override fun apply(request: Request.Builder, originalChain: Interceptor.Chain) {
        if (injector(originalChain.request()))
            request.addHeader("Authorization", "Token " + token)
    }

}