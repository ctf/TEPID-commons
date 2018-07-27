package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.*
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.concurrent.TimeUnit

/**
 * Created by Allan Wang on 2017-05-14.
 *
 * The main user data model with public variables
 * Note that this is typically created from using [FullUser.toUser]
 */
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
        var activeSince: Long = -1,
        var studentId: Int = -1,
        var jobExpiration: Long = TimeUnit.DAYS.toMillis(7), //why is this here
        var colorPrinting: Boolean = false
) : TepidDb by TepidDbDelegate() {

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
) : TepidJackson

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
        var activeSince: Long = System.currentTimeMillis(),
        var studentId: Int = -1,
        var jobExpiration: Long = TimeUnit.DAYS.toMillis(7),//why is this here
        var colorPrinting: Boolean = false
) : TepidDb by TepidDbDelegate() {

    /**
     * Adds information relating to the name of a student to a FullUser [user]
     */
    fun updateUserNameInformation() {
        salutation = if (user.nick == null)
            if (!preferredName.isEmpty()) preferredName[preferredName.size - 1]
            else givenName else nick
        if (!preferredName.isEmpty())
            realName = preferredName.asReversed().joinToString(" ")
        else
            realName = "${givenName} ${lastName}"
    }

    override var type: String? = "user"

    /**
     * Check if supplied [name] matches
     * [shortUser] or [longUser]
     */
    fun isMatch(name: String) =
            if (name.contains(".")) longUser == name
            else shortUser == name

    /**
     * Get the set of semesters for which the user has had courses
     */
    @JsonIgnore
    fun getSemesters(): Set<Semester> =
            courses.map(Course::semester).toSet()

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
    ).withDbData(this)

    fun toNameUser(): NameUser = NameUser(
            displayName = displayName,
            givenName = givenName,
            middleName = middleName,
            lastName = lastName,
            shortUser = shortUser,
            longUser = longUser,
            email = email,
            nick = nick,
            salutation = salutation,
            preferredName = preferredName
    )
}

/**
 * Non db User variant with only name data
 */
data class NameUser(
        var displayName: String? = null,
        var givenName: String? = null,
        var middleName: String? = null,
        var lastName: String? = null,
        var shortUser: String? = null,
        var longUser: String? = null,
        var email: String? = null,
        var nick: String? = null,
        var realName: String? = null,
        var salutation: String? = null,
        var preferredName: List<String> = emptyList()
)

