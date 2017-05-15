package ca.mcgill.science.tepid.common

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

/**
 * Created by Allan Wang on 2017-05-14.
 */
class UserTest {

    @Test
    fun equality() {
        val user1 = User(displayName = "Bob", password = "very secure")
        val user2 = User(displayName = "Bob", password = "very secure")
        assertEquals(user1, user2, "User equality is done by attributes")
    }
}