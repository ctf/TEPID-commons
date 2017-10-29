package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.models.DestinationJson
import ca.mcgill.science.tepid.models.bindings.TEPID_URL_TEST
import org.junit.Test

/**
 * Created by Allan Wang on 2017-10-28.
 */
class ApiTest {

    val api: ITepid by TepidApi(TEPID_URL_TEST)

    @Test
    fun test() {
        val data = DestinationJson()
        data._id = "abc"
        println(data)
    }

}