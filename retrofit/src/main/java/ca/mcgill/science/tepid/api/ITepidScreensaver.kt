package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.models.data.Destination
import ca.mcgill.science.tepid.models.data.MarqueeData
import ca.mcgill.science.tepid.models.data.PrintJob
import ca.mcgill.science.tepid.models.data.PrintQueue
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ITepidScreensaver {

    /**
     * -------------------------------------------
     * Queues
     * -------------------------------------------
     */

    @GET("screensaver/queues")
    @MinAuthority(NONE)
    fun getQueues(): Call<List<PrintQueue>>

    @GET("screensaver/queues/{queue}")
    @MinAuthority(NONE)
    fun listJobs(@Path("queue") queueName: String, @Query("limit") limit: Int, @Query("from") from: Long): Call<Collection<PrintJob>>

    @GET("screensaver/queues/status")
    @MinAuthority(NONE)
    fun getQueueStatus(): Call<Map<String, Boolean>>

    /**
     * -------------------------------------------
     * Marquee
     * -------------------------------------------
     */

    @GET("screensaver/marquee")
    @MinAuthority(NONE)
    fun getMarquee(): Call<List<MarqueeData>>

    /**
     * -------------------------------------------
     * Destinations
     * -------------------------------------------
     */

    @GET("screensaver/destinations")
    @MinAuthority(NONE)
    fun getDestinations(): Call<Map<String, Destination>>

    /**
     * -------------------------------------------
     * Users
     * -------------------------------------------
     */

    @GET("screensaver/{username}")
    @MinAuthority(NONE)
    fun getUserNick(@Path("username") username: String): Call<String>

}

private const val NONE = "none"
