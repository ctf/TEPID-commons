package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.models.internal.Base64
import com.fasterxml.jackson.annotation.JsonIgnore

data class Session(
        var role: String = "",
        var user: FullUser,
        var expiration: Long = -1,
        var persistent: Boolean = true
) : TepidDb by TepidDbDelegate() {

    override var type: String? = "session"

    override fun toString() = "Session ${getId()}"

    override fun equals(other: Any?) = !_id.isNullOrBlank() && other is Session && _id == other._id && _rev == other._rev

    override fun hashCode() = getId().hashCode() * 13 + getRev().hashCode()

    val authHeader: String
        @JsonIgnore
        get() = Base64.encodeToString("${user.shortUser}:$_id".toByteArray(),
                Base64.NO_WRAP or Base64.URL_SAFE)

    fun isValid() = expiration == -1L || expiration > System.currentTimeMillis()

    companion object {
        fun getTokenFromHeader(header: String): String =
                String(Base64.decode(header, Base64.NO_WRAP or Base64.URL_SAFE))
    }
}
