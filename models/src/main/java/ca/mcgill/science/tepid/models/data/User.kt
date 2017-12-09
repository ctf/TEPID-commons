package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.*
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
data class User(
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
        @Transient
        var password: String? = null,
        var groups: List<String> = emptyList(),
        var preferredName: List<String> = emptyList(),
        var activeSince: Date? = null,
        var studentId: Int = -1,
        var jobExpiration: Long = TimeUnit.DAYS.toMillis(7),//why is this here
        var colorPrinting: Boolean = false
) : TepidDb by TepidDbDelegate(), TepidExtras by TepidExtrasDelegate() {
    override var type: String? = "user"
}

/**
 * User Query; student info from autoSuggest
 * A shorter version of user
 */
data class UserQuery(
        var displayName: String = "",
        var shortUser: String = "",
        var email: String = "",
        var colorPrinting: Boolean = false,
        var type: String = "user"
)