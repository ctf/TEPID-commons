package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.models.DTO.QuotaData
import ca.mcgill.science.tepid.models.bindings.CTFER
import ca.mcgill.science.tepid.models.bindings.ELDER
import ca.mcgill.science.tepid.models.bindings.USER
import ca.mcgill.science.tepid.models.data.About
import ca.mcgill.science.tepid.models.data.Destination
import ca.mcgill.science.tepid.models.data.DestinationTicket
import ca.mcgill.science.tepid.models.data.FullDestination
import ca.mcgill.science.tepid.models.data.FullSession
import ca.mcgill.science.tepid.models.data.PersonalIdentifier
import ca.mcgill.science.tepid.models.data.PrintJob
import ca.mcgill.science.tepid.models.data.PrintQueue
import ca.mcgill.science.tepid.models.data.PutResponse
import ca.mcgill.science.tepid.models.data.Session
import ca.mcgill.science.tepid.models.data.SessionRequest
import ca.mcgill.science.tepid.models.data.User
import ca.mcgill.science.tepid.models.data.UserQuery
import com.google.common.io.ByteStreams
import okhttp3.MediaType
import okhttp3.RequestBody
import org.tukaani.xz.LZMA2Options
import org.tukaani.xz.XZOutputStream
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.ByteArrayOutputStream
import java.io.InputStream

/**
 * API version 2.0.0
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

    @DELETE("sessions")
    fun endCurrentSession(): Call<Void>

    /**
     * ends all of the sessions for a specified short
     */

    @POST("sessions/invalidate/{id}")
    fun invalidateSessions(@Path("id") id: String): Call<Void>

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
    @GET("users/{personalIdentifier}")
    @MinAuthority(USER)
    fun getUser(@Path("personalIdentifier") personalIdentifier: PersonalIdentifier): Call<User>

    /**
     * Helper for when sam is an id
     *
     * See [getUser]
     */
    @GET("users/{personalIdentifier}?noRedirect")
    @MinAuthority(USER)
    fun getUser(@Path("personalIdentifier") id: Int): Call<User>

    /**
     * Sets the color toggle for the given user
     * Users can only set their own; CTFers and Elders can set for anyone
     */
    @PUT("users/{id}/color")
    @MinAuthority(USER)
    fun enableColor(@Path("id") id: String, @Body enable: Boolean): Call<PutResponse>

    /**
     * Sets the exchange student status for the given user
     */
    @PUT("users/{id}/exchange")
    @MinAuthority(CTFER)
    fun setExchange(@Path("id") id: String, @Body enable: Boolean): Call<PutResponse>

    /**
     * Sets the new job expiration, in milliseconds
     * Users can only set their own; CTFers and Elders can set for anyone
     */
    @PUT("users/{id}/jobExpiration")
    @MinAuthority(USER)
    fun setJobExpiration(@Path("id") id: String, @Body jobExpiration: Long): Call<PutResponse>

    /**
     * Sets the nickname for the given user
     * Users can only set their own; CTFers and Elders can set for anyone
     * Clearing the nickname is done by submitting an empty body
     */
    @PUT("users/{id}/nick")
    @MinAuthority(USER)
    fun setNickname(@Path("id") id: String, @Body nickname: String): Call<PutResponse>

    /**
     * Get quota of the supplied sam
     * Users can only set their own; CTFers and Elders can set for anyone
     */
    @GET("users/{id}/quota")
    @MinAuthority(USER)
    fun getQuota(@Path("id") id: String): Call<QuotaData>

    /**
     * Gets a list of short users similar to the one supplied
     * query will be used to match short users or display names
     */
    @GET("users/autosuggest/{like}")
    @MinAuthority(CTFER)
    fun queryUsers(@Path("like") query: String, @Query("limit") limit: Int): Call<List<UserQuery>>

    /**
     * Refreshes the user from LDAP and invalidates their sessions
     */
    @POST("users/{id}/refresh")
    @MinAuthority(CTFER)
    fun forceRefresh(@Path("id") id: String): Call<Void>

    /*
     * -------------------------------------------
     * Destinations
     * -------------------------------------------
     */

    /**
     * Retrieve a map of _id to destinations
     */
    @GET("destinations")
    @MinAuthority(USER)
    fun getDestinations(): Call<Map<String, Destination>>

    @POST("destinations")
    @MinAuthority(ELDER)
    fun newDestination(): Call<PutResponse>

    @GET("destinations/{dest}")
    @MinAuthority(USER)
    fun getDestination(@Path("dest") id: String): Call<Destination>

    @PUT("destinations/{dest}")
    @MinAuthority(ELDER)
    fun putDestination(@Path("dest") id: String, @Body destinations: FullDestination): Call<PutResponse>

    @DELETE("destinations/{dest}")
    @MinAuthority(ELDER)
    fun deleteDestination(@Path("dest") id: String): Call<Void>

    /**
     * Send a ticket to the given printer by id
     */
    @POST("destinations/{dest}/ticket")
    @MinAuthority(CTFER)
    fun setTicket(@Path("dest") id: String, @Body ticket: DestinationTicket): Call<PutResponse>

    @DELETE("destinations/{id}/ticket")
    @MinAuthority(CTFER)
    fun deleteTicket(@Path("dest") id: String): Call<Void>

    /*
     * -------------------------------------------
     * Queues
     * -------------------------------------------
     */

    /**
     * Retrieve a map of _id to queues
     */
    @GET("queues")
    @MinAuthority(USER)
    fun getQueues(): Call<Map<String, PrintQueue>>

    @POST("queues")
    @MinAuthority(ELDER)
    fun newQueue(): Call<PutResponse>

    @GET("queues/{queue}")
    @MinAuthority(USER)
    fun getQueue(@Path("queue") id: String): Call<PrintQueue>

    @PUT("queues/{queue}")
    @MinAuthority(ELDER)
    fun putQueue(@Path("queue") id: String, @Body queue: PrintQueue): Call<PutResponse>

    @DELETE("queues/{queue}")
    @MinAuthority(ELDER)
    fun deleteQueue(@Path("queue") id: String): Call<Void>

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
     * Create a print job
     */
    @POST("jobs")
    @MinAuthority(USER)
    fun createNewJob(@Body job: PrintJob): Call<PutResponse>

    /**
     * Get the print job with the [PrintJob._id]
     */
    @GET("jobs/{id}")
    @MinAuthority(USER)
    fun getJob(@Path("id") id: String): Call<PrintJob>

    /**
     * Add job data to the supplied id
     */
    @PUT("jobs/{id}/data")
    @MinAuthority(USER)
    fun addJobData(@Path("id") id: String, @Body input: RequestBody): Call<PutResponse>

    /**
     * Refund the print job with the supplied [PrintJob._id]
     */
    @PUT("jobs/{id}/refunded")
    @MinAuthority(CTFER)
    fun refundJob(@Path("id") id: String, @Body refund: Boolean): Call<PutResponse>

    /**
     * Reprint the print job with the supplied id
     */
    @POST("jobs/{id}/reprint")
    @MinAuthority(USER)
    fun reprintJob(@Path("id") id: String): Call<PutResponse>

    /**
     * Get the list of print jobs for the given user
     *
     * todo add limit query
     */
    @GET("users/{userId}/jobs")
    @MinAuthority(USER)
    fun getUserPrintJobs(@Path("userId") id: String): Call<List<PrintJob>>

    /**
     * Get the list of print jobs for the given queue
     */
    @GET("queues/{queue}/jobs")
    @MinAuthority(NONE)
    fun getPrintJobs(@Path("queue") queue: String, @Query("limit") limit: Int): Call<List<PrintJob>>

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
}

private const val NONE = "none"

/*
 * -------------------------------------------
 * Extensions
 * -------------------------------------------
 */

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
fun ITepid.addJobDataFromInput(id: String, input: InputStream): Call<PutResponse> {
    val o = ByteArrayOutputStream()
    val xzStream = XZOutputStream(o, LZMA2Options())
    ByteStreams.copy(input, xzStream)
    xzStream.finish()
    val body = RequestBody.create(MediaType.parse("application/octet-stream"), o.toByteArray())
    return addJobData(id, body)
}