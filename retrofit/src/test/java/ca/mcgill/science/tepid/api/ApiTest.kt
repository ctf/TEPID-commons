package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.data.Destination
import org.junit.Test

/**
 * Created by Allan Wang on 2017-10-28.
 */
class ApiTest {

    @Test
    fun test() {
        val data = Destination()
        data._id = "abc"
        println(data)
    }

}