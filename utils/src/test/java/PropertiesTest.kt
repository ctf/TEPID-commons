import ca.mcgill.science.tepid.utils.DefaultProps
import ca.mcgill.science.tepid.utils.JarPropLoader
import ca.mcgill.science.tepid.utils.PropsAbout
import org.junit.Test
import kotlin.test.assertEquals

class PropertiesTest {
    @Test
    fun testLoadPropsDefault() {
        assertEquals("v1", PropsAbout.LINK_MAIN)
    }

    @Test
    fun testLoadPropsNonDefault() {
        DefaultProps.withName = { fileName ->
            listOf(
                    JarPropLoader("/Other$fileName")
            )
        }
        assertEquals("v1other", PropsAbout.LINK_MAIN)
    }
}