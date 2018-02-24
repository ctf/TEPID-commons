package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.api
import ca.mcgill.science.tepid.api.internal.get
import org.junit.Test

class JobTest {

    @Test
    fun getJobList() {
        api.getUserPrintJobs("***REMOVED***").get()
    }

}