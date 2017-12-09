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
class TokenInterceptor(private val token: () -> String) : BaseInterceptor() {

    override fun apply(request: Request.Builder, originalChain: Interceptor.Chain) {
        val token = token()
        if (token.isNotBlank())
            request.addHeader("Authorization", "Token $token")
    }

}