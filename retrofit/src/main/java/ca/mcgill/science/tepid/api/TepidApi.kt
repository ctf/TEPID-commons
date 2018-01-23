package ca.mcgill.science.tepid.api

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
 * val API: ITepid by  lazy { TepidApi(...).create() }
 */
class TepidApi(private val url: String,
               private val debug: Boolean = url != TEPID_URL_PRODUCTION) {

    var clientBuilder: (builder: OkHttpClient.Builder) -> Unit = {}

    var debugBuilder: (builder: OkHttpClient.Builder) -> Unit = {
        it.addInterceptor(HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BASIC))
    }

    var retrofitBuilder: (builder: Retrofit.Builder) -> Unit = {}

    fun create(builder: Config.() -> Unit = {}): ITepid {
        val config = Config()
        config.builder()
        val client = OkHttpClient.Builder()
        clientBuilder(client)
        if (debug) debugBuilder(client)

        config.tokenRetriever?.apply { client.addInterceptor(TokenInterceptor(this)) }

        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())

        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(MoshiConverterFactory.create(moshi.build()))
                .client(client.build())
        retrofitBuilder(retrofit)
        return retrofit.build().create(ITepid::class.java)
    }

    class Config {

        var tokenRetriever: (() -> String)? = null
        var auth: Pair<String, String>? = null

    }

}