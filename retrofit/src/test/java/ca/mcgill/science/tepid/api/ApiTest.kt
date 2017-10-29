package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.data.DestinationJson
import org.junit.Test

/**
 * Created by Allan Wang on 2017-10-28.
 */
class ApiTest {

    @Test
    fun test() {
        val data = DestinationJson()
        data._id = "abc"
        println(data)
    }

}