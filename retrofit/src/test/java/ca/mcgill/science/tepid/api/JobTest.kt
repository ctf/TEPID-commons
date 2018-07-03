package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.test.TestUtils
import ca.mcgill.science.tepid.test.get
import org.junit.Test

class JobTest {

    @Test
    fun getJobList() {
        TestUtils.testApi.getUserPrintJobs("testSU").get()
    }

}