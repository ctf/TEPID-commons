package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.api
import ca.mcgill.science.tepid.api.internal.get
import ca.mcgill.science.tepid.api.internal.getFuture
import ca.mcgill.science.tepid.models.data.PrintJob
import ca.mcgill.science.tepid.models.enums.Room
import ca.mcgill.science.tepid.test.TestUtils
import org.junit.Assume
import org.junit.Test
import kotlin.test.assertEquals

class JobModifyingTest {

    companion object {

        init {
            Assume.assumeTrue("Testing mutable job tests", TestUtils.isNotProduction)
        }

    }

    @Test
    fun createInvalidJob() {
        // printjob must have valid queueName
        api.createNewJob(PrintJob()).executeExpectingError(400)
    }

    @Test
    fun getJobChanges() {
        val now = System.currentTimeMillis()
        val job = api.createNewJob(PrintJob(name = "PrintTest $now", queueName = Room._1B16.toString())).get()
        val futureChanges = api.getJobChanges(job.id).getFuture()
        Thread.sleep(100)
        api.refundJob(job.id).get()
        val changes = futureChanges.get()
        assertEquals(job.id, changes.getOrNull(0)?.id)
    }

}