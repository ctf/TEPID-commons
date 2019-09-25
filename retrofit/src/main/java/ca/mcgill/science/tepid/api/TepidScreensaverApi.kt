package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.utils.PropsURL
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * An open ended implementation of the api holder
 *
 * Can be implemented per project with a singleton via
 *
 * val API: ITepidScreensaver by  lazy { TepidScreensaverApi(...).create() }
 */
class TepidScreensaverApi(private val url: String,
               private val debug: Boolean = PropsURL.TESTING?.toBoolean() ?: true,
               private val cacheMaxAge: Int = 5,
               private val cacheMaxStale: Int = 5) {

    var clientBuilder: (builder: OkHttpClient.Builder) -> Unit = {}

    var debugBuilder: (builder: OkHttpClient.Builder) -> Unit = {
        it.addInterceptor(HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BASIC))
    }

    var retrofitBuilder: (builder: Retrofit.Builder) -> Unit = {}

    fun create(): ITepidScreensaver {
        val client = OkHttpClient.Builder()
        clientBuilder(client)
        if (debug) debugBuilder(client)
        client.addNetworkInterceptor({
            it.proceed(it.request()).newBuilder()
                    .header("Cache-Control", String.format("max-age=%d, only-if-cached, max-stale=%d", cacheMaxAge, cacheMaxStale))
                    .build()
        })

        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())

        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                                .addConverterFactory(ScalarsConverterFactory.create())
.addConverterFactory(MoshiConverterFactory.create(moshi.build()))
                .client(client.build())
        retrofitBuilder(retrofit)
        return retrofit.build().create(ITepidScreensaver::class.java)
    }

}