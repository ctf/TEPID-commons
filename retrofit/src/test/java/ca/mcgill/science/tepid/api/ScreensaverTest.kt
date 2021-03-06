package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.TEST_USER_SHORT
import ca.mcgill.science.tepid.test.TestUtils
import ca.mcgill.science.tepid.test.get
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class ScreensaverTest {

    @Test
    fun getQueues() {
        TestUtils.testScreensaverApi.getQueues().get()
    }

    @Test
    fun listJobs() {
        val testQueue = TestUtils.testScreensaverApi.getQueues().get()[0].name ?: fail ("Could not get queues to query")
        TestUtils.testScreensaverApi.listJobs(testQueue).get()
    }

    @Test
    fun listJobsOptions() {
        val testQueue = TestUtils.testScreensaverApi.getQueues().get()[0].name ?: fail ("Could not get queues to query")
        TestUtils.testScreensaverApi.listJobs(testQueue, 5, 5).get()
    }

    @Test
    fun getStatus() {
        TestUtils.testScreensaverApi.getQueueStatus().get()
    }

    @Test
    fun getMarquee() {
        TestUtils.testScreensaverApi.getMarquee().get()
    }

    @Test
    fun getDestinations() {
        TestUtils.testScreensaverApi.getDestinations().get()
    }

    @Test
    fun getUserNick(){
        TestUtils.testApi.setNickname(TestUtils.testUser, "Guinea Pig").get()
        TestUtils.testScreensaverApi.getUserNick(TEST_USER_SHORT).get()
    }
}