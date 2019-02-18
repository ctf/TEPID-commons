import ca.mcgill.science.tepid.utils.DefaultProps
import ca.mcgill.science.tepid.utils.JarPropLoader
import ca.mcgill.science.tepid.utils.PropHolder
import ca.mcgill.science.tepid.utils.PropsAbout
import org.junit.Test
import kotlin.test.assertEquals

class PropertiesTest {

    @Test
    fun testLoadPropsDefault() {
        assertEquals("v1", PropsAbout.LINK_MAIN)
    }

    @Test
    fun testLoadPropsChangedDefault() {

        DefaultProps.withName = { fileName ->
            listOf(
                    JarPropLoader("/Other$fileName")
            )
        }

        assertEquals("v1other", NewPropsAbout.LINK_MAIN)
    }

    companion object {
        object NewPropsAbout : PropHolder(DefaultProps.withName("creationInformation.properties")) {
            val LINK_MAIN by this.get("LINK_MAIN")
            val LINK_TOS by this.get("LINK_TOS")
            val ORG_NAME by this.get("ORG_NAME")
        }
    }
}