package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.api
import ca.mcgill.science.tepid.api.internal.executeTest
import ca.mcgill.science.tepid.models.data.PrintJob
import org.junit.Test

class JobTest {

    @Test
    fun getJobList() {
        api.getUserPrintJobs("***REMOVED***").executeTest()
    }

    @Test
    fun newJob() {
        api.createNewJob(PrintJob(name = "hello",
                queueName = "1B17-***REMOVED***",
                pages = 3
        )).executeTest()
    }

}