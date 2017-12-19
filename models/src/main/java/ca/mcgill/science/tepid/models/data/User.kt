package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.models.bindings.TepidExtras
import ca.mcgill.science.tepid.models.bindings.TepidExtrasDelegate
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Allan Wang on 2017-05-14.
 *
 * The main user data model with public variables
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
        var email: String? = null,
        var faculty: String? = null,
        var nick: String? = null,
        var realName: String? = null,
        var salutation: String? = null,
        var authType: String? = null,
        var role: String = "",
        var preferredName: List<String> = emptyList(),
        var activeSince: Date? = null,
        var studentId: Int = -1,
        var jobExpiration: Long = TimeUnit.DAYS.toMillis(7),//why is this here
        var colorPrinting: Boolean = false
) : TepidDb by TepidDbDelegate(), TepidExtras by TepidExtrasDelegate() {
    override var type: String? = "user"

    fun isMatch(name: String) =
            if (name.contains(".")) longUser == name
            else shortUser == name

    fun toUserQuery() = UserQuery(
            displayName = displayName ?: realName ?: givenName ?: "",
            shortUser = shortUser ?: "",
            email = email ?: "",
            colorPrinting = colorPrinting
    )
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

/**
 * The complete collection of user attributes
 * Note that this is for back end only as it has content that should not be queried
 * BEWARE that the password is printed in user.toString(). It's also a public variable...
 */
data class FullUser(
        var displayName: String? = null,
        var givenName: String? = null,
        var middleName: String? = null,
        var lastName: String? = null,
        var shortUser: String? = null,
        var longUser: String? = null,
        var email: String? = null,
        var faculty: String? = null,
        var nick: String? = null,
        var realName: String? = null,
        var salutation: String? = null,
        var authType: String? = null,
        var role: String = "",
        var password: String? = null,
        var groups: List<String> = emptyList(),
        var courses: List<Course> = emptyList(),
        var preferredName: List<String> = emptyList(),
        var activeSince: Date? = null,
        var studentId: Int = -1,
        var jobExpiration: Long = TimeUnit.DAYS.toMillis(7),//why is this here
        var colorPrinting: Boolean = false
) : TepidDb by TepidDbDelegate() {

    fun isMatch(name: String) =
            if (name.contains(".")) longUser == name
            else shortUser == name

    fun toUser(): User = User(
            displayName = displayName,
            givenName = givenName,
            middleName = middleName,
            lastName = lastName,
            shortUser = shortUser,
            longUser = longUser,
            email = email,
            faculty = faculty,
            nick = nick,
            salutation = salutation,
            authType = authType,
            role = role,
            preferredName = preferredName,
            activeSince = activeSince,
            studentId = studentId,
            jobExpiration = jobExpiration,
            colorPrinting = colorPrinting
    ).apply {
        _id = this@FullUser._id
        _rev = this@FullUser._rev
    }
}