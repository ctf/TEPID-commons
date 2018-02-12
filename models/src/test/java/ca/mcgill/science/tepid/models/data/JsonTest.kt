package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class JsonTest {

    companion object {

        private val mapper: ObjectMapper by lazy { jacksonObjectMapper() }

        private const val REV = "_rev"
        private const val ID = "_id"

        private inline fun <reified T : Any> T.passThroughJackson(): T =
                mapper.treeToValue(mapper.valueToTree(this))

        /**
         * Simplified sanity test where there exists a constructor
         * without any mandatory parameters
         */
        private inline fun <reified T : TepidDb> sanityTest() =
                sanityTest { T::class.java.newInstance() }

        /**
         * A sanity test guarantees that _id and _rev do not exist until created
         * And that they have the right keys
         */
        private inline fun <T : TepidDb> sanityTest(supplier: () -> T) {
            val blank = supplier()
            val msg = "found in ${blank::class.java}:"
            assertNull(blank._id)
            assertNull(blank._rev)
            val blankJson = mapper.valueToTree<JsonNode>(blank)
            assertNull(blankJson.get(ID), "$ID $msg $blankJson")
            assertNull(blankJson.get(REV), "$REV $msg $blankJson")

            val full = supplier()
            val id = blank.hashCode().toString()
            val rev = full.hashCode().toString()
            full._id = id
            full._rev = rev
            val fullJson = mapper.valueToTree<JsonNode>(full)
            assertNotNull(fullJson.get(ID), "No $ID $msg $fullJson")
            assertNotNull(fullJson.get(REV), "No $REV $msg $fullJson")
            val fullDup = mapper.treeToValue(fullJson, full::class.java)
            assertEquals(full, fullDup)
        }

        /**
         * Validates the json conversion to and from the given model
         */
        private inline fun <reified T : Any> checkModel(model: T = T::class.java.newInstance(),
                                                        action: T.() -> Unit) {
            model.action()
            val json = mapper.valueToTree<JsonNode>(model)
            val newModel = mapper.treeToValue<T>(json)
            assertEquals(model, newModel, "Model mismatch for ${T::class.java}")
        }
    }


    @Test
    fun checkedIn() = sanityTest<CheckedIn>()

    @Test
    fun email() = sanityTest<EmailReasons>()

    @Test
    fun destination() {
        sanityTest<Destination>()
        sanityTest<FullDestination>()
    }

    @Test
    fun printJob() {
        sanityTest<PrintJob>()
        checkModel<PrintJob> {
            fail("test")
        }
    }

    @Test
    fun queue() = sanityTest<PrintQueue>()

    @Test
    fun session() {
        sanityTest { FullSession(user = FullUser()) }
    }

    @Test
    fun publicSession() {
        val session = FullSession(user = FullUser())
        session._id = "hello"
        val publicSession = session.toSession().passThroughJackson()
        assertEquals(session._id, publicSession._id)
    }

    @Test
    fun user() {
        sanityTest<User>()
        sanityTest<FullUser>()
    }
}