package ca.mcgill.science.tepid.ldap

import ca.mcgill.science.tepid.test.TEST_AUTH
import org.junit.Assume
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.fail


class LdapTest : LdapBase() {

    companion object {

        init {
            Assume.assumeTrue("Testing LDAP connection",
                    LdapBase().queryUser("***REMOVED***", TEST_AUTH) != null)
        }

    }

    /**
     * Thank you yiwei for volunteering as tribute
     */
    @Test
    fun bindOtherUser() {
        val user = queryUser("***REMOVED***", TEST_AUTH) ?: fail("Null user")
        assertEquals(-1, user.studentId, "unexpected studentId from external TEST_AUTH")
    }

    @Test
    fun bind() {
        val user = queryUser("azsedzzz", TEST_AUTH)
        assertNull(user, "User azsedzzz should be null")
    }

}
