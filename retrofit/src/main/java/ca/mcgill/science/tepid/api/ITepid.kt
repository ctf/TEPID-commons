package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.models.DTO.QuotaData
import ca.mcgill.science.tepid.models.bindings.CTFER
import ca.mcgill.science.tepid.models.bindings.ELDER
import ca.mcgill.science.tepid.models.bindings.USER
import ca.mcgill.science.tepid.models.data.*
import ca.mcgill.science.tepid.models.enums.PrinterId
import com.google.common.io.ByteStreams
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.tukaani.xz.LZMA2Options
import org.tukaani.xz.XZOutputStream
import retrofit2.Call
import retrofit2.http.*
import java.io.ByteArrayOutputStream
import java.io.InputStream

/**
 * API version 1.02.00
 */
interface ITepid {

    /*
     * -------------------------------------------
     * Sessions
     * -------------------------------------------
     */

    /**
     * Check if the session token is valid
     * If 200 response is returned, the token is still good
     */
    @GET("sessions/{user}/{token}")
    @MinAuthority(NONE)
    fun validateToken(@Path("user") user: String, @Path("token") token: String): Call<Session>

    /**
     * Retrieve a new session given the request body
     * The output will contain the token information used to authenticate
     * for all other requests. See [FullSession] for more info.
     */
    @POST("sessions")
    @MinAuthority(NONE)
    fun getSession(@Body body: SessionRequest): Call<Session>

    /**
     * Invalidate the supplied session id
     * See [FullSession.id]
     */
    @DELETE("sessions/{id}")
    @MinAuthority(NONE)
    fun removeSession(@Path("id") id: String): Call<Void>

    /*
     * -------------------------------------------
     * Users
     *
     * Note that for the context of querying,
     * sam refers to shortUser, longUser, or studentId
     * Due to typings, extension methods will be added for queries
     * by studentId
     * -------------------------------------------
     */

    /**
     * Fetches a user model from the given sam
     *
     * Unsuccessful responses:
     * 404  user could not be found
     * 401  invalid session or unauthorized access to another user
     */
    @GET("users/{sam}")
    @MinAuthority(USER)
    fun getUser(@Path("sam") sam: String): Call<User>

    /**
     * Helper for when sam is an id
     *
     * See [getUser]
     */
    @GET("users/{id}?noRedirect")
    @MinAuthority(USER)
    fun getUser(@Path("id") id: Int): Call<User>

    /**
     * Get quota of the supplied sam
     * If sender is a user, the sam must also match their own sam
     * If sender is a ctfer, no restriction is applied on the sam
     */
    @GET("users/{sam}/quota")
    @MinAuthority(USER)
    fun getQuota(@Path("sam") sam: String): Call<QuotaData>

    /**
     * Returns true if a local admin exists
     *
     */
    @GET("users/configured")
    @MinAuthority(NONE)
    fun isConfigured(): Call<Boolean>

    /**
     * Create a new local admin with the given data
     * This is only authorized when no existing local admin exists
     */
    @PUT("users/{sam}")
    @MinAuthority(NONE)
    fun createLocalAdmin(@Path("sam") sam: String, @Body admin: FullUser): Call<PutResponse>

    /**
     * Gets a list of short users similar to the one supplied
     *
     * query will be used to match short users or display names
     */
    @GET("users/autosuggest/{expr}")
    @MinAuthority(CTFER)
    fun queryUsers(@Path("expr") query: String, @Query("limit") limit: Int): Call<List<UserQuery>>

    /**
     * Sets the exchange student status for the given user
     */
    @PUT("users/{sam}/exchange")
    @MinAuthority(CTFER)
    fun setExchange(@Path("sam") sam: String, @Body enable: Boolean): Call<PutResponse>

    /**
     * Sets the color toggle for the given user
     * If sender is a user, the sam must also match their own sam
     * If sender is a ctfer, no restriction is applied on the sam
     */
    @PUT("users/{sam}/color")
    @MinAuthority(USER)
    fun enableColor(@Path("sam") sam: String, @Body enable: Boolean): Call<PutResponse>

    /**
     * Sets the nickname for the given user
     * If sender is a user, the sam must also match their own sam
     * If sender is a ctfer, no restriction is applied on the sam
     *
     * A nickname that is blank will effectively set the nickname to null,
     * and the default name will be used
     */
    @PUT("users/{sam}/nick")
    @MinAuthority(USER)
    fun setNickname(@Path("sam") sam: String, @Body nickname: String): Call<PutResponse>

    /**
     * Sets the new job expiration for the current user
     * If sender is a user, the sam must also match their own sam
     * If sender is a ctfer, no restriction is applied on the sam
     */
    @PUT("users/{sam}/jobExpiration")
    @MinAuthority(USER)
    fun setJobExpiration(@Path("sam") sam: String, @Body jobExpiration: Long): Call<PutResponse>

    /*
     * -------------------------------------------
     * Destinations
     * -------------------------------------------
     */

    /**
     * Retrieve a map of room names to destinations
     */
    @GET("destinations")
    @MinAuthority(USER)
    fun getDestinations(): Call<Map<String, Destination>>

    /**
     * Remap all existing destinations
     */
    @PUT("destinations")
    @MinAuthority(ELDER)
    fun putDestinations(@Body destinations: Map<String, FullDestination>): Call<List<PutResponse>>

    /**
     * Delete the specified destination
     */
    @DELETE("destinations/{printerId}")
    @MinAuthority(ELDER)
    fun deleteDestination(@Path("printerId") printerId: String): Call<String>

    /**
     * Send a ticket to the given printer by id
     */
    @POST("destinations/{printerId}")
    @MinAuthority(CTFER)
    fun setPrinterStatus(@Path("printerId") printerId: String, @Body ticket: DestinationTicket): Call<String>

    /*
     * -------------------------------------------
     * Queues
     * -------------------------------------------
     */

    /**
     * Set the full list of queues
     * ref tepid-commons#12
     */
    @PUT("queues")
    @MinAuthority(ELDER)
    fun putQueues(@Body queues: List<PrintQueue>): Call<List<PutResponse>>

    /**
     * Get the full list of queues
     */
    @GET("queues")
    @MinAuthority(NONE)
    fun getQueues(): Call<List<PrintQueue>>

    @DELETE("queues/{queue}")
    @MinAuthority(ELDER)
    fun deleteQueue(@Path("queue") queue: String): Call<String>

    /**
     * Get the list of print jobs for the given queue
     *
     * todo figure out what the queue is. Likely [PrinterId.serialNumber]?
     */
    @GET("queues/{queue}")
    @MinAuthority(NONE)
    fun getPrintJobs(@Path("queue") queue: String, @Query("limit") limit: Int): Call<List<PrintJob>>

    /**
     * Gets a single print job from the specified queue and id
     *
     * todo return 404 if not found
     */
    @GET("queues/{queue}/{id}")
    @MinAuthority(NONE)
    fun getPrintJob(@Path("queue") queue: String, @Path("id") id: String): Call<PrintJob>

    /**
     * Gets a single file from the specified queue, id, and filename
     *
     * todo this should have at least ctf access?
     */
    @GET("queues/{queue}/{id}/{file}")
    @MinAuthority(NONE)
    fun getAttachment(@Path("queue") queue: String, @Path("id") id: String, @Path("file") file: String): Call<ResponseBody>

    /**
     * Return list of load balancers available
     */
    @GET("queues/loadbalancers")
    @MinAuthority(NONE)
    fun getLoadBalancers(): List<String>

    /*
     * -------------------------------------------
     * Jobs
     * -------------------------------------------
     */

    /**
     * Get the list of print jobs for the given user
     *
     * todo add limit query
     */
    @GET("jobs/{sam}")
    @MinAuthority(USER)
    fun getUserPrintJobs(@Path("sam") sam: String): Call<List<PrintJob>>

    /**
     * Create a print job
     *
     */
    @POST("jobs")
    @MinAuthority(USER)
    fun createNewJob(@Body job: PrintJob): Call<PutResponse>

    /**
     * Add job data to the supplied id
     *
     */
    @PUT("jobs/{id}")
    @MinAuthority(USER)
    fun addJobData(@Path("id") id: String, @Body input: RequestBody): Call<PutResponse>


    /**
     * Get the print job with the [PrintJob._id]
     */
    @GET("jobs/job/{id}")
    @MinAuthority(USER)
    fun getJob(@Path("id") id: String): Call<PrintJob>

    /**
     * Refund the print job with the supplied [PrintJob._id]
     */
    @PUT("jobs/job/{id}/refunded")
    @MinAuthority(CTFER)
    fun refundJob(@Path("id") id: String, @Body refund: Boolean): Call<PutResponse>

    /**
     * Reprint the print job with the supplied id
     */
    @POST("jobs/job/{id}/reprint")
    @MinAuthority(USER)
    fun reprintJob(@Path("id") id: String): Call<String>

    /*
     * -------------------------------------------
     * Misc
     * -------------------------------------------
     */

    /**
     * Retrieve data about the current tepid build
     * Does not require authentication
     */
    @GET("about")
    @MinAuthority(NONE)
    fun getAbout(): Call<About>

    /**
     * Retrieves a string representing the authorized endpoints
     */
    @GET("endpoints")
    @MinAuthority(CTFER)
    fun getEndpoints(): Call<String>

//    @GET("barcode/_wait")
//    fun scanBarcode(): Call<UserBarcode>
}

private const val NONE = "none"

/*
 * -------------------------------------------
 * Extensions
 * -------------------------------------------
 */

/*
 * -------------------------------------------
 * Id extensions
 *
 * Converts ids to strings
 * -------------------------------------------
 */

fun ITepid.setNickname(id: Int, nickname: String) = setNickname(id.toString(), nickname)
fun ITepid.setExchange(id: Int, enable: Boolean) = setExchange(id.toString(), enable)
fun ITepid.enableColor(id: Int, enable: Boolean) = enableColor(id.toString(), enable)
fun ITepid.setJobExpiration(id: Int, jobExpiration: Long) = setJobExpiration(id.toString(), jobExpiration)
fun ITepid.getQuota(id: Int) = getQuota(id.toString())
fun ITepid.getUserPrintJobs(id: Int) = getUserPrintJobs(id.toString())
fun ITepid.refundJob(id: String) = refundJob(id, true)
/*
 * -------------------------------------------
 * Query extensions
 *
 * Supplies defaults to certain queries
 * -------------------------------------------
 */
fun ITepid.queryUsers(query: String) = queryUsers(query, -1)

fun ITepid.getPrintJobs(query: String) = getPrintJobs(query, -1)

/*
 *
 */

/**
 * -------------------------------------------
 * xz extension, so we don't have to circumvent the whole API
 * -------------------------------------------
 */
fun ITepid.addJobDataFromInput( id: String, input: InputStream): Call<PutResponse> {
    val o = ByteArrayOutputStream()
    val xzStream = XZOutputStream(o, LZMA2Options())
    ByteStreams.copy(input, xzStream)
    xzStream.finish()
    val body = RequestBody.create(MediaType.parse("application/octet-stream"), o.toByteArray())
    return addJobData(id, body)
}