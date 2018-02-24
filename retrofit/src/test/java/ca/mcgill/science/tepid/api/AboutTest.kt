package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.apiUnauth
import ca.mcgill.science.tepid.api.internal.get
import org.junit.Test
import kotlin.test.assertNotNull

class AboutTest {

    @Test
    fun get() {
        apiUnauth.getAbout().get().apply {
            assertNotNull(hash, "Did not receive all attributes of about")
        }
    }
}