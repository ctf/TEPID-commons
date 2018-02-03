package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.*
import ca.mcgill.science.tepid.models.internal.Base64
import com.fasterxml.jackson.annotation.JsonIgnore

data class Session(
        var role: String = "",
        var user: FullUser,
        var expiration: Long = -1,
        var persistent: Boolean = true
) : TepidDb by TepidDbDelegate() {

    override var type: String? = "session"

    override fun toString() = "Session $_id"

    override fun equals(other: Any?) = !_id.isNullOrBlank() && other is Session && _id == other._id && _rev == other._rev

    override fun hashCode() = getId().hashCode() * 13 + getRev().hashCode()

    fun isValid() = expiration == -1L || expiration > System.currentTimeMillis()

    fun toPublicSession(): PublicSession = PublicSession(
            user = user.toUser(),
            expiration = expiration,
            persistent = persistent
    ).withIdData(this)


    companion object {

        private const val BASE_64_FLAGS = Base64.NO_WRAP or Base64.URL_SAFE

        /**
         * Encode the supplied [shortUser] and [id] into a Base64 header
         * Naturally, this will only be valid to the server if the supplied
         * parameters are valid
         */
        fun encodeToHeader(shortUser: String?, id: String?): String =
                Base64.encodeToString("$shortUser:$id".toByteArray(), BASE_64_FLAGS)

        /**
         * Decode the input string, returning null if invalid
         * This ensures that the decode always matches the encoder
         * used to find [authHeader]
         */
        fun decodeHeader(header: String): String? =
                try {
                    String(Base64.decode(header, BASE_64_FLAGS))
                } catch (e: Exception) {
                    null
                }
    }
}

/**
 * A session with just a [User]
 */
data class PublicSession(
        var user: User,
        var role: String = user.role,
        var expiration: Long = -1,
        var persistent: Boolean = true
) : TepidId by TepidIdDelegate() {
    val authHeader: String
        @JsonIgnore
        get() = Session.encodeToHeader(user.shortUser, _id)
}