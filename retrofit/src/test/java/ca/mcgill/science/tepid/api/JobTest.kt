package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.models.data.ErrorResponse
import ca.mcgill.science.tepid.models.data.PrintJob
import ca.mcgill.science.tepid.models.data.PutResponse
import ca.mcgill.science.tepid.test.TestUtils
import ca.mcgill.science.tepid.test.get
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Test
import org.tukaani.xz.LZMA2Options
import org.tukaani.xz.XZOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class JobTest {

    @Test
    fun getJobList() {
        TestUtils.testApi.getUserPrintJobs("testSU").get()
    }

    @Test
    fun test() {
        val testFile = "pdf-test.pdf"
        val job = PrintJob(name = TestUtils.testUser,
                queueName = "1B16",
                originalHost = "Unit Test")

        val user = TestUtils.testApi.getUser(TestUtils.testUser).get()


        val putJob = TestUtils.testApi.createNewJob(job).executeDirect()

        assertTrue(putJob!!.ok, "Could not put job")

        val jobId = putJob.id

        println("Sending job data for $jobId")

        val fileInStream = FileInputStream(File(
                this::class.java.classLoader.getResource(testFile).file
        ))

        val o = ByteArrayOutputStream()
        val xzStream = XZOutputStream(o, LZMA2Options())


        xzStream.write(fileInStream.readAllBytes())

        val i = o.toByteArray()

        val response = TestUtils.testApi.addJobData(jobId, i).executeDirect() ?: fail("null response received sending job contents")
        println("Job sent: $response")

        assertTrue(response.ok)
    }

}