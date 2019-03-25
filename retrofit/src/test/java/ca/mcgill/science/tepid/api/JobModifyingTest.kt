package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.executeExpectingError
import ca.mcgill.science.tepid.models.data.PrintJob
import ca.mcgill.science.tepid.test.TestUtils
import org.junit.Assume
import org.junit.Test

class JobModifyingTest {

    companion object {

        init {
            Assume.assumeTrue("Testing mutable job tests", TestUtils.isNotProduction)
        }

    }

    @Test
    fun createInvalidJob() {
        // printjob must have valid queueName
        TestUtils.testApi.createNewJob(PrintJob()).executeExpectingError(400)
    }
}