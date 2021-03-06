import ca.mcgill.science.tepid.utils.FilePropLoader
import ca.mcgill.science.tepid.utils.JarPropLoader
import ca.mcgill.science.tepid.utils.PropHolder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.fail

class PropHolderTest {

    var fileLoader = FilePropLoader("src/test/resources/FilePropLoaderTest.properties")
    var jarLoader = JarPropLoader("/FilePropLoaderTest2.properties")
    var propHolder = PropHolder(listOf(fileLoader, jarLoader))

    @Test
    fun testLoadsSingleFile() {
        val singleHolder = PropHolder(listOf(fileLoader))
        val actual = singleHolder.get("property2").value
        assertEquals("Test2", actual)
    }

    @Test
    fun testFoundFirstAndSecond() {
        val actual = propHolder.get("property1").value
        assertEquals("Test1", actual)
    }

    @Test
    fun testFoundFirstNotInSecond() {
        val actual = propHolder.get("property2").value
        assertEquals("Test2", actual)
    }

    @Test
    fun testMissedFirstInSecond() {
        val actual = propHolder.get("property3").value
        assertEquals("Test3", actual)
    }

    @Test
    fun testMissedInAll() {
        val actual = propHolder.get("doesNotExist").value
        assertEquals(null, actual)
    }

    @Test
    fun testGetNonNullNull() {
        assertThrows<NoSuchElementException>(){ propHolder.getNonNull("doesNotExist").value}
    }

    @Test
    fun testGetNonNullNonNull() {
        val actual = propHolder.getNonNull("property1").value
        assertEquals("Test1", actual)
    }
}