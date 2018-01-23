package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.models.data.*
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Allan Wang on 2017-10-28.
 */
interface ITepid {

    @POST("sessions")
    fun getSession(@Body body: SessionRequest): Call<Session>

    @DELETE("sessions/{id}")
    fun removeSession(@Path("id") id: String): Call<Void>

    @GET("users/{shortUser}")
    fun getUser(@Path("shortUser") shortUser: String): Call<User>

    @GET("users/{id}?noRedirect")
    fun getUser(@Path("id") id: Int): Call<User>

    @GET("users/{shortUser}/quota")
    fun getQuota(@Path("shortUser") shortUser: String): Call<Int>

    /**
     * Returns true if a local admin exists
     * Does not require authentication
     */
    @GET("users/configured")
    fun isConfigured(): Call<Boolean>

    @GET("destinations")
    fun getPrinterInfo(): Call<Map<String, Destination>>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("destinations/{printerId}")
    fun setPrinterStatus(@Path("printerId") printerId: String, @Body body: DestinationTicket): Call<String>

    @GET("queues/{roomId}")
    fun getPrintQueue(@Path("roomId") roomId: String, @Query("limit") limit: Int): Call<List<PrintJob>>

    //TODO add limit query
    @GET("jobs/{shortUser}")
    fun getUserPrintJobs(@Path("shortUser") shortUser: String): Call<List<PrintJob>>

    /**
     * Gets a list of short users similar to the one supplied
     * Only accessible by ctfers and elders
     */
    @GET("users/autosuggest/{expr}")
    fun getUserQuery(@Path("expr") query: String, @Query("limit") limit: Int): Call<List<UserQuery>>

    /**
     * getUserQuery with a default limit of 10
     */
    @GET("users/autosuggest/{expr}?limit=10") //use default query limit
    fun getUserQuery(@Path("expr") query: String): Call<List<UserQuery>>

    @PUT("jobs/job/{id}/refunded")
    fun refund(@Path("id") id: String, @Body refund: Boolean): Call<Void>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @PUT("users/{shortUser}/color")
    fun enableColor(@Path("shortUser") shortUser: String, @Body enable: Boolean): Call<ColorResponse>

    /**
     * Retrieve data abount the current tepid build
     * Does not require authentication
     */
    @GET("about")
    fun getAbout(): Call<About>

    /**
     * Retrieves a string represending the authorized endpoints
     * Does not require authentication
     */
    @GET("endpoints")
    fun getEndpoints(): Call<String>

//    @GET("barcode/_wait")
//    fun scanBarcode(): Call<UserBarcode>
}