package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.test.TestUtils
import ca.mcgill.science.tepid.test.get
import org.junit.Test
import kotlin.test.assertNotNull

class AboutTest {

    @Test
    fun get() {
        TestUtils.testApiUnauth.getAbout().get().apply {
            assertNotNull(hash, "Did not receive all attributes of about")
        }
    }
}