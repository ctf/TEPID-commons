package ca.mcgill.science.tepid.models.data

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import javax.persistence.*
import kotlin.test.assertNotNull


@Entity
data class TestEntity(
        @javax.persistence.Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        @Column(nullable = false)
        val data: String
)

class HibernateTest {

    @Test
    fun testAddObject(){
        em.getTransaction().begin();
        var test = TestEntity(id = null, data = "t")
//        test._id = "testID"
        em.persist(test)
        em.transaction.commit();

        val te = em.find(TestEntity::class.java, test.id);
        assertNotNull(te);
    }

    /*@BeforeEach
    fun initialiseDb(){
        val session = em.unwrap(Session::class.java);
        session.doWork(Work { c: Connection ->
            val script = File(this::class.java.classLoader.getResource("data.sql").file)
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