package ca.mcgill.science.tepid.data

import ca.mcgill.science.tepid.data.bindings.*
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Allan Wang on 2017-05-14.
 *
 * BEWARE that the password is printed in user.toString(). It's also a public variable...
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class UserJson(
        var displayName: String? = null,
        var givenName: String? = null,
        var middleName: String? = null,
        var lastName: String? = null,
        var shortUser: String? = null,
        var longUser: String? = null,
        var email: String,
        var faculty: String? = null,
        var nick: String? = null,
        var realName: String? = null,
        var salutation: String? = null,
        var authType: String? = null,
        var role: String = USER,
        var password: String? = null,
        var groups: List<String> = ArrayList(),
        var preferredName: List<String>? = null,
        var activeSince: Date? = null,
        var studentId: Int = -1,
        var jobExpiration: Long = TimeUnit.DAYS.toMillis(7),//why is this here
        var colorPrinting: Boolean = false
) : TepidDb by TepidDbDelegate(), TepidExtras by TepidExtrasDelegate() {
    override var type: String? = "user"
}

data class User(
        val displayName: String?,
        val givenName: String?,
        val middleName: String?,
        val lastName: String?,
        val shortUser: String?,
        val longUser: String?,
        val email: String,
        val faculty: String?,
        val nick: String?,
        val realName: String?,
        val salutation: String?,
        val authType: String?,
        val role: String,
        val password: String?,
        val groups: List<String>,
        val preferredName: List<String>?,
        val activeSince: Date?,
        val studentId: Int,
        val jobExpiration: Long,
        val colorPrinting: Boolean
)