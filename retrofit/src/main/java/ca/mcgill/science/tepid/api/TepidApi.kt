package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.adapters.DateAdapter
import ca.mcgill.science.tepid.models.bindings.TEPID_URL_PRODUCTION
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Allan Wang on 2017-10-29.
 *
 * An open ended implementation of the api holder
 *
 * Can be implemented per project with a singleton via
 *
 * val API by TepidApi("url")
 */
class TepidApi(val url: String, val debug: Boolean = url != TEPID_URL_PRODUCTION) {

    var token: String = ""

    var clientBuilder: (builder: OkHttpClient.Builder) -> Unit = {}

    var debugBuilder: (builder: OkHttpClient.Builder) -> Unit = {
        it.addInterceptor(HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS))
    }

    var retrofitBuilder: (builder: Retrofit.Builder) -> Unit = {}

    fun create(config: TepidApi.() -> Unit = {}): ITepid {
        config()
        val client = OkHttpClient.Builder()
        clientBuilder(client)
        if (debug) debugBuilder(client)
        client.addInterceptor(TokenInterceptor { token })


        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(DateAdapter())

        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(MoshiConverterFactory.create(moshi.build()))
                .client(client.build())
        retrofitBuilder(retrofit)
        return retrofit.build().create(ITepid::class.java)
    }

}