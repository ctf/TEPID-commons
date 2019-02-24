package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import javax.persistence.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@Entity
data class TestEntity(
//        @javax.persistence.Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        var content: String = ""
): @EmbeddedId TepidDb by TepidDbDelegate()

class HibernateTest {

    @Test
    fun testAddObject(){

        em.transaction.begin();
        val test = TestEntity(content = "t")
        em.persist(test)
        em.transaction.commit();
        val te = em.find(TestEntity::class.java, test._id);

        assertNotNull(te);
        assertEquals(test, te)
        println(te._id)
    }

    @Test
    fun testAddFullUser(){
        em.transaction.begin();
        val testFullUser = FullUser(shortUser = "shortUname")
        em.persist(testFullUser)
        em.transaction.commit()
        val retrievedUser : FullUser = em.find(FullUser::class.java ,testFullUser._id)

        assertNotNull(retrievedUser);
        assertEquals(testFullUser, retrievedUser)
    }

    @Test
    fun testAddCourse(){
        em.transaction.begin();
        val testCourse = Course("TEST101", Season.SUMMER, 1337)
        em.persist(testCourse)
        em.transaction.commit()
        val retrievedCoure = em.find(Course::class.java, Course("TEST101", Season.SUMMER, 1337))

        assertNotNull(retrievedCoure)
        assertEquals(testCourse, retrievedCoure)
        println(retrievedCoure)
    }

    /*@BeforeEach
    fun initialiseDb(){
        val session = em.unwrap(Session::class.java);
        session.doWork(Work { c: Connection ->
            val script = File(this::class.java.classLoader.getResource("content.sql").file)
            RunScript.execute(c, FileReader(script))
        })
    }*/

    companion object {
        lateinit var emf: EntityManagerFactory
        lateinit var em: EntityManager

        @JvmStatic
        @BeforeAll
        fun initTest() {
            emf = Persistence.createEntityManagerFactory("hibernate-pu-test");
            em = emf.createEntityManager();
        }

        @JvmStatic
        @AfterAll
        fun tearTest(){
            em.clear()
            em.close()
            emf.close()
        }
    }
}