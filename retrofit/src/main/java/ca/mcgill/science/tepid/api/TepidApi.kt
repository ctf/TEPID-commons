package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.models.bindings.TEPID_URL_PRODUCTION
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.reflect.KProperty

/**
 * Created by Allan Wang on 2017-10-29.
 *
 * An open ended implementation of the api holder
 *
 * Can be implemented per project with a singleton via
 *
 * val API by TepidApi("url")
 */
class TepidApi(val url: String, val debug: Boolean = url != TEPID_URL_PRODUCTION, val config: TepidApi.() -> Unit = {}) {

    var clientBuilder: (builder: OkHttpClient.Builder) -> Unit = {}

    var debugBuilder: (builder: OkHttpClient.Builder) -> Unit = {
        it.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
    }
    
    var retrofitBuilder: (builder: Retrofit.Builder) -> Unit = {}

    operator fun getValue(thisRef: Any?, property: KProperty<*>): ITepid {
        config()
        val client = OkHttpClient.Builder()
        clientBuilder(client)
        if (debug) debugBuilder(client)
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client.build())
        retrofitBuilder(retrofit)
        return retrofit.build().create(ITepid::class.java)
    }

}