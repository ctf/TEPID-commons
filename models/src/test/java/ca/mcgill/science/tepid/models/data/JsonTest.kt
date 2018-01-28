package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class JsonTest {

    companion object {

        private val mapper: ObjectMapper by lazy { jacksonObjectMapper() }

        private const val REV = "_rev"
        private const val ID = "_id"

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
    fun printJob() = sanityTest<PrintJob>()

    @Test
    fun queue() = sanityTest<PrintQueue>()

    @Test
    fun session() = sanityTest { Session(user = FullUser()) }

    @Test
    fun user() {
        sanityTest<User>()
        sanityTest<FullUser>()
    }
}