package ca.mcgill.science.tepid.ldap

import ca.mcgill.science.tepid.models.data.Course
import ca.mcgill.science.tepid.models.data.FullUser
import ca.mcgill.science.tepid.models.data.Season
import ca.mcgill.science.tepid.models.data.User
import ca.mcgill.science.tepid.utils.WithLogging
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.naming.Context.*
import javax.naming.NamingException
import javax.naming.directory.Attribute
import javax.naming.directory.Attributes
import javax.naming.directory.SearchControls
import javax.naming.ldap.InitialLdapContext
import javax.naming.ldap.LdapContext

object Ldap : WithLogging() {

    /**
     * Queries [username] with [auth] credentials (username to password).
     * Resulting user is nonnull if it exists
     *
     * Note that [auth] may use different credentials than the [username] in question.
     * However, if a different auth is provided (eg from our science account),
     * the studentId cannot be queried
     */
    @JvmStatic
    fun queryUser(username: String?, auth: Pair<String, String>): FullUser? {
        if (username == null) return null
        val ldapSearchBase = ***REMOVED***
        val searchName = if (username.contains(".")) "userPrincipalName=$username@mail.mcgill.ca" else "sAMAccountName=$username"
        val searchFilter = "(&(objectClass=user)($searchName))"
        val ctx = bindLdap(auth) ?: return null
        val searchControls = SearchControls()
        searchControls.searchScope = SearchControls.SUBTREE_SCOPE
        val results = ctx.search(ldapSearchBase, searchFilter, searchControls)
        val searchResult = results.nextElement()
        results.close()
        val user = searchResult?.attributes?.toUser(ctx)
        ctx.close()
        return user
    }

    /**
     * Make sure that the regex matches values located in [Season]
     */
    private val seasonRegex = Regex("ou=(fall|winter|summer) (2[0-9]{3})[^0-9]")

    /**
     * Creates a blank user and attempts to retrieve as many attributes
     * as possible from the specified attributes
     *
     * Note that when converting
     */
    private fun Attributes.toUser(ctx: LdapContext): FullUser {
        fun attr(name: String) = get(name)?.get()?.toString() ?: ""
        val out = FullUser(
                displayName = attr("displayName"),
                givenName = attr("givenName"),
                lastName = attr("sn"),
                shortUser = attr("sAMAccountName"),
                longUser = attr("userPrincipalName").toLowerCase(),
                email = attr("mail"),
                middleName = attr("middleName"),
                faculty = attr("department"),
                studentId = attr("employeeID").toIntOrNull() ?: -1
        )
        try {
            out.activeSince = SimpleDateFormat("yyyyMMddHHmmss.SX").parse(attr("whenCreated"))
        } catch (e: ParseException) {

        }

        val members = get("memberOf")?.toList()?.mapNotNull {
            try {
                val cn = ctx.getAttributes(it, arrayOf("CN"))?.get("CN")?.get()?.toString()
                val groupValues = seasonRegex.find(it.toLowerCase(Locale.CANADA))?.groupValues
                val semester = if (groupValues != null) Season.valueOf(groupValues[1].toUpperCase(Locale.CANADA)) to groupValues[2].toInt()
                else null
                cn to semester
            } catch (e: NamingException) {
                null
            }
        }

        val groups = mutableListOf<String>()

        val courses = mutableListOf<Course>()

        members?.forEach { (name, semester) ->
            if (name == null) return@forEach
            if (semester == null) groups.add(name)
            else courses.add(Course(name, semester.first, semester.second))
        }

        out.groups = groups
        out.courses = courses

        return out
    }

    /**
     * Copy over attributes from another user
     */
    private fun FullUser.mergeFrom(other: FullUser?) {
        other ?: return
        _id = other._id
        _rev = other._rev
        studentId = other.studentId
        preferredName = other.preferredName
        nick = nick ?: other.nick
        colorPrinting = other.colorPrinting
        jobExpiration = other.jobExpiration
        shortUser = shortUser ?: other.shortUser
        if (studentId <= 0) studentId == other.studentId
    }

    /**
     * Convert attributes to attribute list
     */
    private fun Attributes.toList(): List<Attribute> {
        val ids = iDs
        val data = mutableListOf<Attribute>()
        while (ids.hasMore()) {
            val id = ids.next()
            data.add(get(id))
        }
        ids.close()
        return (data)
    }

    /**
     * Convert attribute to string list
     */
    private fun Attribute.toList() = (0 until size()).map { get(it).toString() }

    /**
     * Defines the environment necessary for [InitialLdapContext]
     */
    private fun createAuthMap(user: String, password: String) = Hashtable<String, String>().apply {
        put(SECURITY_AUTHENTICATION, "simple")
        put(INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory")
        put(PROVIDER_URL, ***REMOVED***)
        put(SECURITY_PRINCIPAL, "***REMOVED***\\$user")
        put(SECURITY_CREDENTIALS, password)
        put("com.sun.jndi.ldap.read.timeout", "5000")
        put("com.sun.jndi.ldap.connect.timeout", "500")
    }

    private fun bindLdap(auth: Pair<String, String>) = bindLdap(auth.first, auth.second)

    /**
     * Create [LdapContext] for given credentials
     */
    private fun bindLdap(user: String, password: String): LdapContext? {
        try {
            val auth = createAuthMap(user, password)
            return InitialLdapContext(auth, null)
        } catch (e: Exception) {
            log.error("Failed to bind to LDAP")
            return null
        }
    }

}