package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.*
import ca.mcgill.science.tepid.models.internal.Base64
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class FullSession(
        var role: String = "",
        @Access(AccessType.FIELD)
        @ManyToOne(targetEntity = FullUser::class, fetch = FetchType.EAGER)
        var user: FullUser,
        var expiration: Long = -1L,
        var persistent: Boolean = true
) : TepidDb(type="session") {

    @Transient
    fun isUnexpired() = expiration == -1L || expiration > System.currentTimeMillis()

    fun toSession(): Session = Session(
            user = user.toUser(),
            expiration = expiration,
            persistent = persistent
    ).withIdData(this)
}

/**
 * A session with just a [User]
 */
data class Session(
        var user: User,
        var role: String = user.role,
        var expiration: Long = -1L,
        var persistent: Boolean = true
) : TepidDb() {
    val authHeader: String
        @JsonIgnore
        get() = encodeToHeader(user.shortUser, _id)

    fun isValid() = expiration == -1L || expiration > System.currentTimeMillis()

    companion object {

        private const val BASE_64_FLAGS = Base64.NO_WRAP or Base64.URL_SAFE

        /**
         * Encode the supplied [shortUser] and [id] into a Base64 header
         * Naturally, this will only be valid to the server if the supplied
         * parameters are valid
         */
        fun encodeToHeader(shortUser: String?, id: String?): String =
                encodeToHeader("$shortUser:$id")

        fun encodeToHeader(token: String): String =
                Base64.encodeToString(token.toByteArray(), BASE_64_FLAGS)

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