package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.executeTest
import ca.mcgill.science.tepid.models.bindings.TEPID_URL_TEST
import org.junit.Test
import kotlin.test.assertNotNull

/**
 * Created by Allan Wang on 2017-10-28.
 */
class ApiTest {

    val api: ITepid by TepidApi(TEPID_URL_TEST)

    @Test
    fun about() {
        api.getAbout().executeTest().apply {
            assertNotNull(hash)
        }
    }

}