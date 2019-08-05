package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.executeExpectingError
import ca.mcgill.science.tepid.models.data.PrintJob
import ca.mcgill.science.tepid.test.TestUtils
import org.junit.jupiter.api.Test
class JobModifyingTest {
    @Test
    fun createInvalidJob() {
        // printjob must have valid queueName
        TestUtils.testApi.createNewJob(PrintJob()).executeExpectingError(400)
    }
}