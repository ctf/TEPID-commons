package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.models.bindings.TepidIdDelegate
import ca.mcgill.science.tepid.models.bindings.withDbData
import ca.mcgill.science.tepid.models.bindings.withIdData
import org.junit.Test
import kotlin.test.assertEquals

class BindingTest {

    @Test
    fun propagationDb() {
        val orig = TepidDbDelegate()
        orig._id = "hello"
        val next = TepidDbDelegate().withDbData(orig)
        assertEquals(orig._id, next._id)
    }

    @Test
    fun propagationId() {
        val orig = TepidDbDelegate()
        orig._id = "hello"
        val next = TepidIdDelegate().withIdData(orig)
        assertEquals(orig._id, next._id)
    }

}