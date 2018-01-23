package ca.mcgill.science.tepid.ldap

import ca.mcgill.science.tepid.test.TestUtils
import org.junit.Assume
import org.junit.Test
import kotlin.test.*


class LdapTest : LdapBase() {

    companion object {

        private const val ***REMOVED*** = "***REMOVED***"

        init {
            Assume.assumeTrue("Testing LDAP connection",
                    LdapBase().queryUser(***REMOVED***, TestUtils.TEST_AUTH) != null)
        }

    }

    @Test
    fun bindSelf() {
        val user = queryUser(***REMOVED***, TestUtils.TEST_AUTH)
        assertNotNull(user)
        println(user!!)
        assertEquals("Ctf Science", user.displayName)
        assertEquals(***REMOVED***, user.shortUser)
        assertTrue(user.groups.contains("***REMOVED***"))
        println(user)
    }

    /**
     * Thank you yiwei for volunteering as tribute
     */
    @Test
    fun bindOtherUser() {
        val shortUser = "***REMOVED***"
        val user = queryUser(shortUser, TestUtils.TEST_AUTH) ?: fail("Null user")
        println(user)
        assertEquals(shortUser, user.shortUser, "short user mismatch")
        assertEquals(-1, user.studentId, "unexpected studentId from external TEST_AUTH")
    }

    @Test
    fun bind() {
        val user = queryUser("azsedzzz", TestUtils.TEST_AUTH)
        assertNull(user, "User azsedzzz should be null")
    }

}
