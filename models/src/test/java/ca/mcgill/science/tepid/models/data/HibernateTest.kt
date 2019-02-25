package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import org.junit.Ignore
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.Serializable
import javax.persistence.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@Embeddable
data class TestEmbeddable(var data:String) : Serializable

@Entity
data class TestEmbedding(
        @Embedded
        @ElementCollection(targetClass = TestEmbeddable::class)
        var datas : HashSet<TestEmbeddable>
) : @EmbeddedId TepidDb by TepidDbDelegate()

@Entity
data class TestEntity(
//        @javax.persistence.Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        var content: String = ""
) : @EmbeddedId TepidDb by TepidDbDelegate()

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
    fun testAddEmbeddingObject(){
        em.transaction.begin();
        val e1 = TestEmbeddable("1")
        val e2 = TestEmbeddable("2")
        val test = TestEmbedding(HashSet(setOf(e1,e2)))
        em.persist(test)
        em.transaction.commit();
        val te = em.find(TestEmbedding::class.java, test._id);

        assertNotNull(te);
        assertEquals(test, te)
        println(te._id)
    }

    @Ignore
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
        val retrievedCourse = em.find(Course::class.java, Course("TEST101", Season.SUMMER, 1337))

        assertNotNull(retrievedCourse)
        assertEquals(testCourse, retrievedCourse)
        println(retrievedCourse)
    }

    @Test
    fun testQueryCourse(){
        em.transaction.begin();
        val testCourse = Course("TEST101", Season.SUMMER, 1337)
        em.persist(testCourse)
        em.transaction.commit()

        val jpql = "select e from Course e where e.name = :name"

        val retrievedCourse : Course = em.createQuery(jpql, Course::class.java)
                .setParameter("name", "TEST101")
                .singleResult

        println(retrievedCourse)
        assertEquals(testCourse, retrievedCourse)

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