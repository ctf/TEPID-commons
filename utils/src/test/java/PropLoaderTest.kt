import ca.mcgill.science.tepid.utils.FilePropLoader
import ca.mcgill.science.tepid.utils.JarPropLoader
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.fail

/**
 * only seems to work when launched from the gradle task
 */
class FilePropLoaderTest {

    @Test
    fun testLoadFileNotExist(){
        val h = FilePropLoader("does/not/exist/")
        val actual = h.loadProps()
        assertEquals(false, actual, "Does not return false if file does not exist")
    }

    @Test
    fun testLoadFileExists(){
        val h = FilePropLoader("src/test/resources/FilePropLoaderTest.properties")
        val actual = h.loadProps()
        assertEquals(true, actual, "Does not return true if file does exist;\nCurrently only works when run from gradle, tho, so check that")
    }

    @Test
    fun testGetPropertyNotExists(){
        val h = FilePropLoader("src/test/resources/FilePropLoaderTest.properties")
        h.loadProps()
        val actual = h.get("nonExistentProperty")
        assertEquals(null, actual, "Does not return null if property does not exist;\nCurrently only works when run from gradle, tho, so check that")
    }

    @Test
    fun testGetPropertyExists(){
        val h = FilePropLoader("src/test/resources/FilePropLoaderTest.properties")
        h.loadProps()
        val actual = h.get("property2")
        assertEquals("Test2", actual, "Does not return property if property does not exist;\nCurrently only works when run from gradle, tho, so check that")
    }
}

class JarPropLoaderTest {

    @Test
    fun testLoadFileNotExist(){
        val h = JarPropLoader("does/not/exist/")
        val actual = h.loadProps()
        assertEquals(false, actual, "Does not return false if file does not exist")
    }

    @Test
    fun testLoadFileExists(){
        val h = JarPropLoader("FilePropLoaderTest.properties")
        val actual = h.loadProps()
        assertEquals(true, actual, "Does not return true if file does exist")
    }

    @Test
    fun testGetPropertyNotExists(){
        val h = JarPropLoader("FilePropLoaderTest.properties")
        h.loadProps()
        val actual = h.get("nonExistentProperty")
        assertEquals(null, actual, "Does not return null if property does not exist")

    }

    @Test
    fun testGetPropertyExists(){
        val h = JarPropLoader("FilePropLoaderTest.properties")
        h.loadProps()
        val actual = h.get("property2")
        assertEquals("Test2", actual, "Does not return property if property does not exist")
    }
}