
import ca.mcgill.science.tepid.utils.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileWriter
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

class PropSaverTest {

    @Test
    fun testSavePropsNewFile(){
        val filePath="src/test/resources/FilePropSaverTest0.properties"
        val props: PropSaver = FilePropLoader(filePath)
        props.set("k0","v0")
        props.saveProps()

        val ver: PropLoader = FilePropLoader(filePath)
        assertEquals("v0", ver.get("k0"))

        assertTrue(File(filePath).delete(), "Failed to delete file")
    }

    @Test
    fun testSavePropsExisting(){
        val filePath="src/test/resources/FilePropSaverTest1.properties"
        FileWriter(filePath).use{f->f.write("#Sat Aug 10 18:53:29 EDT 2019\nk0=v0\n")}

        val props = FilePropLoader(filePath)
        assertEquals("v0", props.get("k0"))
        props.set("k0","v1")
        props.saveProps()

        val ver: PropLoader = FilePropLoader(filePath)
        assertEquals("v1", ver.get("k0"))
        assertTrue(File(filePath).delete(), "Failed to delete file")
    }

    companion object {
    }
}