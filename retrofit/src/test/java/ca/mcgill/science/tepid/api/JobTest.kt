package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.models.data.DestinationTicket
import ca.mcgill.science.tepid.models.data.FullDestination
import ca.mcgill.science.tepid.models.data.PrintJob
import ca.mcgill.science.tepid.models.data.PrintQueue
import ca.mcgill.science.tepid.test.TestUtils
import ca.mcgill.science.tepid.test.get
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.TimeUnit

class JobTest {

    lateinit var testJob: PrintJob

    @BeforeEach
    fun initTest() {
        val r = TestUtils.testApi.enableColor(TestUtils.testUser, true).execute()

        val d0 = "d0".padEnd(36)
        val d1 = "d1".padEnd(36)

        TestUtils.testApi.putDestinations(mapOf(d0 to FullDestination(name = d0, up = true), d1 to FullDestination(name = d1, up = true))).executeDirect()

        val q =  PrintQueue(loadBalancer = "fiftyfifty", name = "0", destinations = listOf(d0, d1))
        q._id = "q0"
        TestUtils.testApi.putQueues(listOf(q)).executeDirect()

        testJob = PrintJob(
                name = "Server Test ${System.currentTimeMillis()}",
                queueId = "q0",
                userIdentification = TestUtils.testUser
        )
    }

    @Test
    fun getJobList() {
        TestUtils.testApi.getUserPrintJobs("testSU").get()
    }

    @Test
    fun test() {
        val testFile = "pdf-test.pdf"

        val putJob = TestUtils.testApi.createNewJob(testJob).executeDirect()

        assertTrue(putJob!!.ok, "Could not put job")

        val jobId = putJob.id

        println("Sending job data for $jobId")

        val response = TestUtils.testApi
                .addJobDataFromInput(jobId, FileInputStream(File(this::class.java.classLoader.getResource(testFile).file)))
                .executeDirect() ?: fail("null response received sending job contents")
        println("Job sent: $response")

        assertTrue(response.ok)
    }

    /* This test lives here because it turns out to be a good test for all sorts of stupid serialisation stuff
     * - setting color sends a naked boolean
     * - several endpoints return naked strings
     * - some endpoints do weird stuff with extracting responses
     * and most of the serialisation happens on this end
     */
    @org.junit.jupiter.api.Test
    fun testReprint() {
        val testFile = "pdf-test.pdf"

        val putJob = TestUtils.testApi.createNewJob(testJob).executeDirect()
        assertTrue(putJob!!.ok, "Could not put job")
        val jobId = putJob.id

        println("Sending job data for $jobId")

        // print once
        val response = TestUtils.testApi.addJobDataFromInput(jobId, FileInputStream(File(this::class.java.classLoader.getResource(testFile).file)))
                .executeDirect() ?: fail("null response received sending job contents")
        println("Job sent: $response")

        assertTrue(response.ok)

        // turn off original destination
        TimeUnit.MILLISECONDS.sleep(2500)

        val printedJob = TestUtils.testApi.getJob(jobId).executeDirect()
                ?: fail("did not retrieve printed job after print")

        val setStatusResponse = TestUtils.testApi.setPrinterStatus(printedJob.destination
                ?: fail("printed job did not have destination"), DestinationTicket(up = false, reason = "reprint test, put me up")).execute()

        if (setStatusResponse?.body()?.contains("down") != true) {
            fail("destination was not marked down")
        }

        // reprint
        val reprintResponse = TestUtils.testApi.reprintJob(jobId).execute().body()
                ?: fail("did not retrieve response after reprint")


        assertFalse(reprintResponse.contains("Failed"))

        val foundIds = TestUtils.GUID_REGEX.findAll( reprintResponse).map { f -> f.value }.toList()
        assertEquals(2,foundIds.size)
        assertEquals(jobId, foundIds[0])
        assertNotEquals(jobId, foundIds[1])


    }

}