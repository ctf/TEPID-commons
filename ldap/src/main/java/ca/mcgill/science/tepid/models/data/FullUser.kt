package ca.mcgill.science.tepid.models.data

import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Allan Wang on 2017-12-14.
 *
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
) {

    internal var _id: String = ""
    internal var _rev: String? = null

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