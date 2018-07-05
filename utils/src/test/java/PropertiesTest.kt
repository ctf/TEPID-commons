import ca.mcgill.science.tepid.utils.*
import org.junit.Test
import org.junit.Ignore
import kotlin.test.assertEquals
import kotlin.test.fail

class PropertiesTest{
    @Test
    fun testLoadPropsDefault(){
        assertEquals("v1", PropsAbout.LINK_MAIN)
    }

    @Test
    fun testLoadPropsNonDefault(){
        DefaultProps.withName = {fileName -> listOf(
                JarPropLoader("/Other$fileName")
        )}
        assertEquals("v1other", PropsAbout.LINK_MAIN)
    }
}