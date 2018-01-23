package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import java.util.*

data class Session(
        var role: String = "",
        var user: FullUser,
        var expiration: Long = -1,
        var persistent: Boolean = true
) : TepidDb by TepidDbDelegate() {

    override var type: String? = "session"

    override fun toString() = "Session $_id"

    override fun equals(other: Any?) = _id.isNotBlank() && other is Session && _id == other._id && _rev == other._rev

    override fun hashCode() = _id.hashCode() * 13 + (_rev?.hashCode() ?: 7)

    val token: String
        get() = Base64.getEncoder()
                .encodeToString("${user.shortUser}:$_id".toByteArray())

}
