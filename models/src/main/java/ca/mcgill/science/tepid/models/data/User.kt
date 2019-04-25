package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.models.bindings.TepidJackson
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.concurrent.TimeUnit
import javax.persistence.*

/**
 * Note that this is typically created from using [FullUser.toUser]
 */
@Embeddable
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
        var preferredName: String? = null,
        var activeSince: Long = -1,
        var studentId: Int = -1,
        var jobExpiration: Long = TimeUnit.DAYS.toMillis(7),
        var colorPrinting: Boolean = false
) {

    @Transient
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
 * Note that this is the main user model used for the backend.
 * It contains sensitive information, and therefore should not be used for interchange over the network
 * BEWARE that, for builtin users, the hashed password is printed in user.toString().
 */
@Entity
data class FullUser(
        var displayName: String? = null,                    // LDAP authoritative
        var givenName: String? = null,                      // LDAP authoritative
        var middleName: String? = null,                     // LDAP authoritative
        var lastName: String? = null,                       // LDAP authoritative
        var shortUser: String? = null,                      // LDAP authoritative
        var longUser: String? = null,                       // Expected lower case
        var email: String? = null,                          // LDAP authoritative
        var faculty: String? = null,                        // LDAP authoritative
        var nick: String? = null,                           // DB authoritative
        var realName: String? = null,                       // Computed
        var salutation: String? = null,                     // Computed
        var authType: String? = null,
        var role: String = "",                              // Computed
        var password: String? = null,                       // Password encrypted with bcrypt for local users
        @Access(AccessType.FIELD)
        @ElementCollection(fetch = FetchType.EAGER)
        var groups: List<String> = emptyList(),             // Computed, from LDAP
        @Access(AccessType.FIELD)
        @ElementCollection(targetClass = Course::class, fetch = FetchType.EAGER)
        var courses: List<Course> = emptyList(),            // Computer, from LDAP
        var preferredName: String? = null,                  // DB authoritative
        var activeSince: Long = System.currentTimeMillis(), // LDAP authoritative
        var studentId: Int = -1,
        var jobExpiration: Long = TimeUnit.DAYS.toMillis(7), // DB authoritative
        var colorPrinting: Boolean = false // DB authoritative
) : TepidDb(type="user") {

    init {
        updateUserNameInformation()
    }

    /**
     * Adds information relating to the name of a student to a FullUser [user]
     */
    fun updateUserNameInformation() {
        salutation = nick ?: preferredName ?:givenName
        realName = preferredName ?: "${givenName} ${lastName}"
    }

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
    @Transient
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
    )

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
        var preferredName: String? = null
)
